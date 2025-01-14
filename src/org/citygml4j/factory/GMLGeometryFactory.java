/*
 * citygml4j - The Open Source Java API for CityGML
 * https://github.com/citygml4j
 * 
 * Copyright 2013-2017 Claus Nagel <claus.nagel@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 *     
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.citygml4j.factory;

import java.util.ArrayList;
import java.util.List;

import org.citygml4j.model.gml.geometry.primitives.DirectPosition;
import org.citygml4j.model.gml.geometry.primitives.DirectPositionList;
import org.citygml4j.model.gml.geometry.primitives.Exterior;
import org.citygml4j.model.gml.geometry.primitives.Interior;
import org.citygml4j.model.gml.geometry.primitives.LineString;
import org.citygml4j.model.gml.geometry.primitives.LinearRing;
import org.citygml4j.model.gml.geometry.primitives.Point;
import org.citygml4j.model.gml.geometry.primitives.Polygon;

public class GMLGeometryFactory {

	public GMLGeometryFactory() {

	}
	
	public Point createPoint(List<Double> coordinates, int dim) throws DimensionMismatchException {
		Point point = new Point();
		point.setPos(createDirectPosition(coordinates, dim));
		
		return point;
	}
	
	public Point createPoint(double[] coordinates, int dim) throws DimensionMismatchException {
		return createPoint(asList(coordinates), dim);
	}

	public LineString createLineString(List<Double> coordinates, int dim) throws DimensionMismatchException {
		LineString lineString = new LineString();
		lineString.setPosList(createDirectPositionList(coordinates, dim));

		return lineString;
	}

	public LineString createLineString(double[] coordinates, int dim) throws DimensionMismatchException {
		return createLineString(asList(coordinates), dim);
	}

	public Polygon createLinearPolygon(List<Double> coords, int dim) throws DimensionMismatchException {
		Polygon polygon = new Polygon();

		Exterior exterior = new Exterior();
		exterior.setRing(createLinearRing(coords, dim));		
		polygon.setExterior(exterior);

		return polygon;
	}
	
	public Polygon createLinearPolygon(double[] coordinates, int dim) throws DimensionMismatchException {
		return createLinearPolygon(asList(coordinates), dim);
	}

	public Polygon createLinearPolygon(Object[] coordinates, int dim) throws DimensionMismatchException {
		Polygon polygon = null;
		List<List<Double>> coordList = asList(coordinates);

		if (coordList.size() > 0) {
			polygon = createLinearPolygon(coordList.get(0), dim);

			for (int i = 1; i < coordList.size(); i++) {
				LinearRing linearRing = createLinearRing(coordList.get(i), dim);
				Interior interior = new Interior();
				interior.setRing(linearRing);

				polygon.addInterior(interior);
			}
		}

		return polygon;
	}
	
	public Polygon createLinearPolygon(double[][] coordinates, int dim) throws DimensionMismatchException {
		Polygon polygon = null;
		
		if (coordinates.length > 0) {
			polygon = createLinearPolygon(coordinates[0], dim);
			
			for (int i = 1; i < coordinates.length; i++) {
				LinearRing linearRing = createLinearRing(coordinates[i], dim);
				Interior interior = new Interior();
				interior.setRing(linearRing);

				polygon.addInterior(interior);
			}
		}
		
		return polygon;
	}
	
	public DirectPosition createDirectPosition(List<Double> coordinates, int dim) throws DimensionMismatchException {
		if (coordinates.size() != dim)
			throw new DimensionMismatchException();
		
		DirectPosition pos = new DirectPosition();
		pos.setValue(coordinates);
		pos.setSrsDimension(dim);
		
		return pos;
	}
	
	public DirectPosition createDirectPosition(double[] coordinates, int dim) throws DimensionMismatchException {
		return createDirectPosition(asList(coordinates), dim);
	}
	
	public DirectPositionList createDirectPositionList(List<Double> coordinates, int dim) throws DimensionMismatchException {
		if ((coordinates.size() % dim) != 0)
			throw new DimensionMismatchException();

		DirectPositionList posList = new DirectPositionList();
		posList.setValue(coordinates);
		posList.setSrsDimension(dim);
		
		return posList;
	}
	
	public DirectPositionList createDirectPositionList(double[] coordinates, int dim) throws DimensionMismatchException {
		return createDirectPositionList(asList(coordinates), dim);
	}	
	
	public LinearRing createLinearRing(List<Double> coordinates, int dim) throws DimensionMismatchException {
		DirectPositionList posList = createDirectPositionList(coordinates, dim);
		
		boolean addCoord = false;
		for (int i = 0; !addCoord && i < dim; i++)
			if (coordinates.get(i).doubleValue() != coordinates.get(coordinates.size() - dim + i).doubleValue())
				addCoord = true;

		if (addCoord)
			for (int i = 0; i < dim; i++)
				coordinates.add(coordinates.get(i));

		LinearRing linearRing = new LinearRing();
		linearRing.setPosList(posList);

		return linearRing;
	}
	
	public LinearRing createLinearRing(double[] coordinates, int dim) throws DimensionMismatchException {
		return createLinearRing(asList(coordinates), dim);
	}
	
	@SuppressWarnings("unchecked")
	private List<List<Double>> asList(Object[] array) {
		List<List<Double>> list = new ArrayList<List<Double>>(array.length);
		boolean isValid = true;
		
		for (int i = 0; isValid && i < array.length; i++) {
			if (array[i] instanceof List<?>) {
				boolean isDoubleList = true;

				for (Object item : (List<?>)array[i]) {
					if (!(item instanceof Double)) {
						isDoubleList = false;
						break;
					}
				}

				if (isDoubleList)
					list.add((List<Double>)array[i]);
				else 
					isValid = false;
			}

			else if (array[i] instanceof double[])
				list.add(asList((double[])array[i]));

			else
				isValid = false;
		}
		
		return list;
	}
	
	private List<Double> asList(double[] array) {
		List<Double> list = new ArrayList<Double>(array.length);
		for (double item : array)
			list.add(item);

		return list;
	}
}
