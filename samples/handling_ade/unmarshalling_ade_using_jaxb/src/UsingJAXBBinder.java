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

import javax.xml.bind.Binder;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.datatype.XMLGregorianCalendar;

import net.opengis.gml.MultiSurfaceType;
import net.opengis.gml.StringOrRefType;

import org.citygml4j.CityGMLContext;
import org.citygml4j.builder.CityGMLBuilder;
import org.citygml4j.builder.jaxb.JAXBContextPath;
import org.citygml4j.model.citygml.ade.ADEComponent;
import org.citygml4j.model.citygml.core.CityModel;
import org.citygml4j.model.citygml.core.CityObjectMember;
import org.citygml4j.model.module.citygml.CityGMLVersion;
import org.citygml4j.model.module.citygml.CoreModule;
import org.citygml4j.xml.io.CityGMLInputFactory;
import org.citygml4j.xml.io.CityGMLOutputFactory;
import org.citygml4j.xml.io.reader.CityGMLReader;
import org.citygml4j.xml.io.writer.CityModelWriter;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import ade.sub.jaxb.AbstractBoundarySurfaceType;
import ade.sub.jaxb.BoundarySurfacePropertyType;
import ade.sub.jaxb.ClosureSurfaceType;
import ade.sub.jaxb.GroundSurfaceType;
import ade.sub.jaxb.RelativeToTerrainType;
import ade.sub.jaxb.RoofSurfaceType;
import ade.sub.jaxb.TunnelType;
import ade.sub.jaxb.WallSurfaceType;

public class UsingJAXBBinder {

	public static void main(String[] args) throws Exception {
		SimpleDateFormat df = new SimpleDateFormat("[HH:mm:ss] "); 

		System.out.println(df.format(new Date()) + "setting up citygml4j context and JAXB builder");
		CityGMLContext ctx = new CityGMLContext();
		CityGMLBuilder builder = ctx.createCityGMLBuilder();

		System.out.println(df.format(new Date()) + "reading ADE-enriched CityGML file LOD2_SubsurfaceStructureADE_v100.gml");
		System.out.println(df.format(new Date()) + "ADE schema file is read from xsi:schemaLocation attribute on root XML element");
		CityGMLInputFactory in = builder.createCityGMLInputFactory();

		CityGMLReader reader = in.createCityGMLReader(new File("../../datasets/LOD2_SubsurfaceStructureADE_v100.gml"));
		CityModel cityModel = (CityModel)reader.nextFeature();
		reader.close();

		ADEComponent ade = cityModel.getCityObjectMember().get(3).getGenericADEComponent();
		Element adeElement = ade.getContent();

		System.out.println(df.format(new Date()) + "creating JAXBContext from ADE JAXB classes");
		String contextPath = JAXBContextPath.getContextPath("ade.sub.jaxb");
		JAXBContext jaxbCtx = JAXBContext.newInstance(contextPath);

		System.out.println(df.format(new Date()) + "creating JAXB binder and unmarshalling ADE content");
		final Binder<Node> binder = jaxbCtx.createBinder();
		JAXBElement<?> element = (JAXBElement<?>)binder.unmarshal(adeElement);
		System.out.println("Unmarshalled JAXB object: " + element);

		System.out.println(df.format(new Date()) + "processing ADE feature sub:Tunnel by using JAXB binder to modify ADE content");
		if (element.getValue() instanceof TunnelType) {
			TunnelType tunnel = (TunnelType)element.getValue();
			System.out.println("ADE feature: Tunnel, gml:id='" + tunnel.getId() + "'");

			XMLGregorianCalendar creationDate = tunnel.getCreationDate();
			System.out.print("creation date (from CityObject): ");
			System.out.println(creationDate.getYear() + "-" + creationDate.getMonth() + "-" + creationDate.getDay());

			for (JAXBElement<Object> genericProperty : tunnel.get_GenericApplicationPropertyOfCityObject()) {
				if (genericProperty.getValue() instanceof RelativeToTerrainType) {
					RelativeToTerrainType type = (RelativeToTerrainType)genericProperty.getValue();
					System.out.println("relativeToTerrain: " + type.value());
				}					
			}

			for (BoundarySurfacePropertyType boundedBy : tunnel.getBoundedBySurface()) {
				AbstractBoundarySurfaceType boundarySurface = (AbstractBoundarySurfaceType)boundedBy.get_Object().getValue();

				if (boundarySurface instanceof RoofSurfaceType) 
					System.out.println("ADE feature: RoofSurface");
				else if (boundarySurface instanceof ClosureSurfaceType) 
					System.out.println("ADE feature: ClosureSurface");
				else if (boundarySurface instanceof WallSurfaceType) 
					System.out.println("ADE feature: WallSurface");
				else if (boundarySurface instanceof GroundSurfaceType) 
					System.out.println("ADE feature: GroundSurface");

				StringOrRefType description = new StringOrRefType();
				description.setValue("processed by citygml4j");
				boundarySurface.setDescription(description);

				MultiSurfaceType multiSurfaceType = boundarySurface.getLod2MultiSurface().getMultiSurface();
				System.out.println("  Processing geometry: " + multiSurfaceType);
				multiSurfaceType.setDescription(description);

				binder.updateXML(boundarySurface);
			}
		}

		System.out.println(df.format(new Date()) + "writing processed citygml4j object tree");
		CityGMLOutputFactory out = builder.createCityGMLOutputFactory(CityGMLVersion.v1_0_0);

		CityModelWriter writer = out.createCityModelWriter(new File("LOD2_SubsurfaceStructureADE_JAXBBinder_v100.gml"));
		writer.setPrefixes(CityGMLVersion.v1_0_0);
		writer.setPrefix("sub", "http://www.citygml.org/ade/sub/0.9.0");
		writer.setDefaultNamespace(CoreModule.v1_0_0);
		writer.setSchemaLocation("http://citygml.org/ade/sub/0.9.0", "../../datasets/schemas/CityGML-SubsurfaceADE-0_9_0.xsd");
		writer.setIndentString("  ");

		writer.writeStartDocument();

		for (CityObjectMember member : cityModel.getCityObjectMember())
			if (member.isSetGenericADEComponent())
				writer.writeFeatureMember(member.getGenericADEComponent());

		writer.writeEndDocument();		
		writer.close();
		
		System.out.println(df.format(new Date()) + "ADE-enriched CityGML file LOD2_SubsurfaceStructureADE_JAXBBinder_v100.gml written");
		System.out.println(df.format(new Date()) + "sample citygml4j application successfully finished");
	}

}
