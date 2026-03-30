package com.btm.utils;

import org.locationtech.jts.geom.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @description 网格工具类
 * 网格嵌套的情况，计算网格的包含关系，支持按覆盖比例计算
 * @author btm
 * @date 206/03/30 09:48
 */
public class GeoUtil {

    private static final GeometryFactory FACTORY = new GeometryFactory();

    /**
     * 构建多边形
     */
    public static Polygon buildPolygon(List<double[]> coords) {

        if (coords == null || coords.size() < 3) {
            throw new RuntimeException("多边形至少需要3个点");
        }

        Coordinate[] coordinates = coords.stream()
                .map(c -> new Coordinate(c[0], c[1]))
                .toArray(Coordinate[]::new);

        // 闭合
        if (!coordinates[0].equals2D(coordinates[coordinates.length - 1])) {
            coordinates = Arrays.copyOf(coordinates, coordinates.length + 1);
            coordinates[coordinates.length - 1] = coordinates[0];
        }

        return FACTORY.createPolygon(coordinates);
    }

    /**
     * 核心：返回被包含的ID
     */
    public static List<String> getContainedIds(
            List<double[]> managerCoords,
            List<? extends HasPolygon> unitList
    ) {

        Polygon managerPolygon = buildPolygon(managerCoords);
        Envelope env = managerPolygon.getEnvelopeInternal();

        List<String> result = new ArrayList<>();

        for (HasPolygon unit : unitList) {

            Polygon unitPolygon = buildPolygon(unit.getCoords());

            // 外接矩形过滤
            if (!env.intersects(unitPolygon.getEnvelopeInternal())) {
                continue;
            }

            if (managerPolygon.covers(unitPolygon)) {
                result.add(unit.getId());
            }
        }

        return result;
    }

    /**
     * 覆盖比例
     */
    public static double coverageRatio(List<double[]> managerCoords, List<double[]> unitCoords) {
        Polygon managerPolygon = buildPolygon(managerCoords);
        Polygon unitPolygon = buildPolygon(unitCoords);

        Geometry intersection = managerPolygon.intersection(unitPolygon);

        double intersectionArea = intersection.getArea();
        double unitArea = unitPolygon.getArea();

        return unitArea == 0 ? 0 : intersectionArea / unitArea;
    }

    /**
     * 通用接口
     */
    public interface HasPolygon {
        String getId();
        List<double[]> getCoords();
    }
}