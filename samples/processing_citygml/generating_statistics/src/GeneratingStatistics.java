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
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.citygml4j.CityGMLContext;
import org.citygml4j.builder.CityGMLBuilder;
import org.citygml4j.model.citygml.CityGML;
import org.citygml4j.model.citygml.CityGMLClass;
import org.citygml4j.model.citygml.core.CityModel;
import org.citygml4j.model.gml.GMLClass;
import org.citygml4j.model.gml.feature.AbstractFeature;
import org.citygml4j.model.gml.geometry.AbstractGeometry;
import org.citygml4j.util.walker.GMLWalker;
import org.citygml4j.xml.io.CityGMLInputFactory;
import org.citygml4j.xml.io.reader.CityGMLReader;

public class GeneratingStatistics {

	public static void main(String[] args) throws Exception {
		SimpleDateFormat df = new SimpleDateFormat("[HH:mm:ss] "); 

		System.out.println(df.format(new Date()) + "setting up citygml4j context and JAXB builder");
		CityGMLContext ctx = new CityGMLContext();
		CityGMLBuilder builder = ctx.createCityGMLBuilder();

		System.out.println(df.format(new Date()) + "reading CityGML file LOD3_Railway_v200.gml");
		CityGMLInputFactory in = builder.createCityGMLInputFactory();
		CityGMLReader reader = in.createCityGMLReader(new File("../../datasets/LOD3_Railway_v200.gml"));
		CityModel cityModel = (CityModel)reader.nextFeature();
		reader.close();
	
		final HashMap<CityGMLClass, Integer> features = new HashMap<CityGMLClass, Integer>();
		final HashMap<GMLClass, Integer> geometries = new HashMap<GMLClass, Integer>();

		System.out.println(df.format(new Date()) + "walking through document and counting features/geometries");
		GMLWalker walker = new GMLWalker() {

			@Override
			public void visit(AbstractFeature abstractFeature) {
				if (abstractFeature instanceof CityGML) {
					CityGMLClass key = ((CityGML)abstractFeature).getCityGMLClass();
					int count = features.containsKey(key) ? features.get(key) + 1 : 1;
					features.put(key, count);
				}				
					
				super.visit(abstractFeature);
			}

			@Override
			public void visit(AbstractGeometry abstractGeometry) {
				GMLClass key = abstractGeometry.getGMLClass();
				int count = geometries.containsKey(key) ? geometries.get(key) + 1 : 1;
				geometries.put(key, count);
				
				super.visit(abstractGeometry);
			}
			
		};
		
		cityModel.accept(walker);

		System.out.println(df.format(new Date()) + "LOD3_Railway_v200.gml contains:");
		System.out.println("Features:");
		for (CityGMLClass feature : features.keySet())
			System.out.println(feature + ": " + features.get(feature));
		
		System.out.println("\nGeometries:");
		for (GMLClass geometry : geometries.keySet())
			System.out.println(geometry + ": " + geometries.get(geometry));
		
		System.out.println(df.format(new Date()) + "sample citygml4j application successfully finished");
	}
}
