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
package org.citygml4j.builder.jaxb.marshal.citygml.building;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;

import net.opengis.citygml.building._1.AbstractBoundarySurfaceType;
import net.opengis.citygml.building._1.AbstractBuildingType;
import net.opengis.citygml.building._1.AbstractOpeningType;
import net.opengis.citygml.building._1.BoundarySurfacePropertyType;
import net.opengis.citygml.building._1.BuildingFurnitureType;
import net.opengis.citygml.building._1.BuildingInstallationPropertyType;
import net.opengis.citygml.building._1.BuildingInstallationType;
import net.opengis.citygml.building._1.BuildingPartPropertyType;
import net.opengis.citygml.building._1.BuildingPartType;
import net.opengis.citygml.building._1.BuildingType;
import net.opengis.citygml.building._1.CeilingSurfaceType;
import net.opengis.citygml.building._1.ClosureSurfaceType;
import net.opengis.citygml.building._1.DoorType;
import net.opengis.citygml.building._1.FloorSurfaceType;
import net.opengis.citygml.building._1.GroundSurfaceType;
import net.opengis.citygml.building._1.IntBuildingInstallationPropertyType;
import net.opengis.citygml.building._1.IntBuildingInstallationType;
import net.opengis.citygml.building._1.InteriorFurniturePropertyType;
import net.opengis.citygml.building._1.InteriorRoomPropertyType;
import net.opengis.citygml.building._1.InteriorWallSurfaceType;
import net.opengis.citygml.building._1.ObjectFactory;
import net.opengis.citygml.building._1.OpeningPropertyType;
import net.opengis.citygml.building._1.RoofSurfaceType;
import net.opengis.citygml.building._1.RoomType;
import net.opengis.citygml.building._1.WallSurfaceType;
import net.opengis.citygml.building._1.WindowType;

import org.citygml4j.builder.jaxb.marshal.JAXBMarshaller;
import org.citygml4j.builder.jaxb.marshal.citygml.CityGMLMarshaller;
import org.citygml4j.model.citygml.ade.ADEComponent;
import org.citygml4j.model.citygml.building.AbstractBoundarySurface;
import org.citygml4j.model.citygml.building.AbstractBuilding;
import org.citygml4j.model.citygml.building.AbstractOpening;
import org.citygml4j.model.citygml.building.BoundarySurfaceProperty;
import org.citygml4j.model.citygml.building.Building;
import org.citygml4j.model.citygml.building.BuildingFurniture;
import org.citygml4j.model.citygml.building.BuildingInstallation;
import org.citygml4j.model.citygml.building.BuildingInstallationProperty;
import org.citygml4j.model.citygml.building.BuildingModuleComponent;
import org.citygml4j.model.citygml.building.BuildingPart;
import org.citygml4j.model.citygml.building.BuildingPartProperty;
import org.citygml4j.model.citygml.building.CeilingSurface;
import org.citygml4j.model.citygml.building.ClosureSurface;
import org.citygml4j.model.citygml.building.Door;
import org.citygml4j.model.citygml.building.FloorSurface;
import org.citygml4j.model.citygml.building.GroundSurface;
import org.citygml4j.model.citygml.building.IntBuildingInstallation;
import org.citygml4j.model.citygml.building.IntBuildingInstallationProperty;
import org.citygml4j.model.citygml.building.InteriorFurnitureProperty;
import org.citygml4j.model.citygml.building.InteriorRoomProperty;
import org.citygml4j.model.citygml.building.InteriorWallSurface;
import org.citygml4j.model.citygml.building.OpeningProperty;
import org.citygml4j.model.citygml.building.OuterCeilingSurface;
import org.citygml4j.model.citygml.building.OuterFloorSurface;
import org.citygml4j.model.citygml.building.RoofSurface;
import org.citygml4j.model.citygml.building.Room;
import org.citygml4j.model.citygml.building.WallSurface;
import org.citygml4j.model.citygml.building.Window;
import org.citygml4j.model.citygml.core.AddressProperty;
import org.citygml4j.model.common.base.ModelObject;
import org.citygml4j.model.gml.basicTypes.Code;

public class Building100Marshaller {
	private final ObjectFactory bldg = new ObjectFactory();
	private final JAXBMarshaller jaxb;
	private final CityGMLMarshaller citygml;

	public Building100Marshaller(CityGMLMarshaller citygml) {
		this.citygml = citygml;
		jaxb = citygml.getJAXBMarshaller();
	}

	public JAXBElement<?> marshalJAXBElement(Object src) {
		JAXBElement<?> dest = null;

		if (src instanceof BuildingModuleComponent)
			src = marshal((BuildingModuleComponent)src);

		if (src instanceof BuildingType)
			dest = bldg.createBuilding((BuildingType)src);		
		else if (src instanceof BuildingFurnitureType)
			dest = bldg.createBuildingFurniture((BuildingFurnitureType)src);
		else if (src instanceof BuildingInstallationType)
			dest = bldg.createBuildingInstallation((BuildingInstallationType)src);
		else if (src instanceof BuildingPartType)
			dest = bldg.createBuildingPart((BuildingPartType)src);
		else if (src instanceof CeilingSurfaceType)
			dest = bldg.createCeilingSurface((CeilingSurfaceType)src);
		else if (src instanceof ClosureSurfaceType)
			dest = bldg.createClosureSurface((ClosureSurfaceType)src);
		else if (src instanceof DoorType)
			dest = bldg.createDoor((DoorType)src);
		else if (src instanceof FloorSurfaceType)
			dest = bldg.createFloorSurface((FloorSurfaceType)src);
		else if (src instanceof GroundSurfaceType)
			dest = bldg.createGroundSurface((GroundSurfaceType)src);
		else if (src instanceof IntBuildingInstallationType)
			dest = bldg.createIntBuildingInstallation((IntBuildingInstallationType)src);
		else if (src instanceof InteriorWallSurfaceType)
			dest = bldg.createInteriorWallSurface((InteriorWallSurfaceType)src);
		else if (src instanceof RoofSurfaceType)
			dest = bldg.createRoofSurface((RoofSurfaceType)src);
		else if (src instanceof RoomType)
			dest = bldg.createRoom((RoomType)src);
		else if (src instanceof WallSurfaceType)
			dest = bldg.createWallSurface((WallSurfaceType)src);
		else if (src instanceof WindowType)
			dest = bldg.createWindow((WindowType)src);

		return dest;
	}

	public Object marshal(ModelObject src) {
		Object dest = null;

		if (src instanceof BoundarySurfaceProperty)
			dest = marshalBoundarySurfaceProperty((BoundarySurfaceProperty)src);
		else if (src instanceof Building)
			dest = marshalBuilding((Building)src);
		else if (src instanceof BuildingFurniture)
			dest = marshalBuildingFurniture((BuildingFurniture)src);
		else if (src instanceof BuildingInstallation)
			dest = marshalBuildingInstallation((BuildingInstallation)src);
		else if (src instanceof BuildingInstallationProperty)
			dest = marshalBuildingInstallationProperty((BuildingInstallationProperty)src);
		else if (src instanceof BuildingPart)
			dest = marshalBuildingPart((BuildingPart)src);
		else if (src instanceof BuildingPartProperty)
			dest = marshalBuildingPartProperty((BuildingPartProperty)src);
		else if (src instanceof CeilingSurface)
			dest = marshalCeilingSurface((CeilingSurface)src);
		else if (src instanceof ClosureSurface)
			dest = marshalClosureSurface((ClosureSurface)src);
		else if (src instanceof Door)
			dest = marshalDoor((Door)src);
		else if (src instanceof FloorSurface)
			dest = marshalFloorSurface((FloorSurface)src);
		else if (src instanceof GroundSurface)
			dest = marshalGroundSurface((GroundSurface)src);
		else if (src instanceof OuterCeilingSurface)
			dest = marshalOuterCeilingSurface((OuterCeilingSurface)src);
		else if (src instanceof OuterFloorSurface)
			dest = marshalOuterFloorSurface((OuterFloorSurface)src);
		else if (src instanceof IntBuildingInstallation)
			dest = marshalIntBuildingInstallation((IntBuildingInstallation)src);
		else if (src instanceof IntBuildingInstallationProperty)
			dest = marshalIntBuildingInstallationProperty((IntBuildingInstallationProperty)src);
		else if (src instanceof InteriorFurnitureProperty)
			dest = marshalInteriorFurnitureProperty((InteriorFurnitureProperty)src);
		else if (src instanceof InteriorRoomProperty)
			dest = marshalInteriorRoomProperty((InteriorRoomProperty)src);
		else if (src instanceof InteriorWallSurface)
			dest = marshalInteriorWallSurface((InteriorWallSurface)src);
		else if (src instanceof OpeningProperty)
			dest = marshalOpeningProperty((OpeningProperty)src);
		else if (src instanceof RoofSurface)
			dest = marshalRoofSurface((RoofSurface)src);
		else if (src instanceof Room)
			dest = marshalRoom((Room)src);
		else if (src instanceof WallSurface)
			dest = marshalWallSurface((WallSurface)src);
		else if (src instanceof Window)
			dest = marshalWindow((Window)src);

		return dest;
	}

	public void marshalAbstractBuilding(AbstractBuilding src, AbstractBuildingType dest) {
		citygml.getCore100Marshaller().marshalAbstractSite(src, dest);

		if (src.isSetClazz())
			dest.setClazz(src.getClazz().getValue());

		if (src.isSetFunction()) {
			for (Code function : src.getFunction())
				dest.getFunction().add(function.getValue());
		}

		if (src.isSetUsage()) {
			for (Code usage : src.getUsage())
				dest.getUsage().add(usage.getValue());
		}

		if (src.isSetYearOfConstruction()) {
			try {
				GregorianCalendar date = src.getYearOfConstruction();
				DatatypeFactory factory = DatatypeFactory.newInstance();
				dest.setYearOfConstruction(factory.newXMLGregorianCalendarDate(
						date.get(Calendar.YEAR),
						date.get(Calendar.MONTH) + 1,
						date.get(Calendar.DAY_OF_MONTH),
						DatatypeConstants.FIELD_UNDEFINED));
			} catch (DatatypeConfigurationException e) {
				// 
			}
		}

		if (src.isSetYearOfDemolition()) {
			try {
				GregorianCalendar date = src.getYearOfDemolition();
				DatatypeFactory factory = DatatypeFactory.newInstance();
				dest.setYearOfDemolition(factory.newXMLGregorianCalendarDate(
						date.get(Calendar.YEAR),
						date.get(Calendar.MONTH) + 1,
						date.get(Calendar.DAY_OF_MONTH),
						DatatypeConstants.FIELD_UNDEFINED));
			} catch (DatatypeConfigurationException e) {
				// 
			}
		}

		if (src.isSetRoofType())
			dest.setRoofType(src.getRoofType().getValue());

		if (src.isSetMeasuredHeight())
			dest.setMeasuredHeight(jaxb.getGMLMarshaller().marshalLength(src.getMeasuredHeight()));

		if (src.isSetStoreysAboveGround())
			dest.setStoreysAboveGround(BigInteger.valueOf(src.getStoreysAboveGround()));

		if (src.isSetStoreysBelowGround())
			dest.setStoreysBelowGround(BigInteger.valueOf(src.getStoreysBelowGround()));

		if (src.isSetStoreyHeightsAboveGround())
			dest.setStoreyHeightsAboveGround(jaxb.getGMLMarshaller().marshalMeasureOrNullList(src.getStoreyHeightsAboveGround()));

		if (src.isSetStoreyHeightsBelowGround())
			dest.setStoreyHeightsBelowGround(jaxb.getGMLMarshaller().marshalMeasureOrNullList(src.getStoreyHeightsBelowGround()));

		if (src.isSetLod1Solid())
			dest.setLod1Solid(jaxb.getGMLMarshaller().marshalSolidProperty(src.getLod1Solid()));

		if (src.isSetLod2Solid())
			dest.setLod2Solid(jaxb.getGMLMarshaller().marshalSolidProperty(src.getLod2Solid()));

		if (src.isSetLod3Solid())
			dest.setLod3Solid(jaxb.getGMLMarshaller().marshalSolidProperty(src.getLod3Solid()));

		if (src.isSetLod4Solid())
			dest.setLod4Solid(jaxb.getGMLMarshaller().marshalSolidProperty(src.getLod4Solid()));

		if (src.isSetLod1MultiSurface())
			dest.setLod1MultiSurface(jaxb.getGMLMarshaller().marshalMultiSurfaceProperty(src.getLod1MultiSurface()));

		if (src.isSetLod2MultiSurface())
			dest.setLod2MultiSurface(jaxb.getGMLMarshaller().marshalMultiSurfaceProperty(src.getLod2MultiSurface()));

		if (src.isSetLod3MultiSurface())
			dest.setLod3MultiSurface(jaxb.getGMLMarshaller().marshalMultiSurfaceProperty(src.getLod3MultiSurface()));

		if (src.isSetLod4MultiSurface())
			dest.setLod4MultiSurface(jaxb.getGMLMarshaller().marshalMultiSurfaceProperty(src.getLod4MultiSurface()));

		if (src.isSetLod1TerrainIntersection())
			dest.setLod1TerrainIntersection(jaxb.getGMLMarshaller().marshalMultiCurveProperty(src.getLod1TerrainIntersection()));

		if (src.isSetLod2TerrainIntersection())
			dest.setLod2TerrainIntersection(jaxb.getGMLMarshaller().marshalMultiCurveProperty(src.getLod2TerrainIntersection()));

		if (src.isSetLod3TerrainIntersection())
			dest.setLod3TerrainIntersection(jaxb.getGMLMarshaller().marshalMultiCurveProperty(src.getLod3TerrainIntersection()));

		if (src.isSetLod4TerrainIntersection())
			dest.setLod4TerrainIntersection(jaxb.getGMLMarshaller().marshalMultiCurveProperty(src.getLod4TerrainIntersection()));

		if (src.isSetLod2MultiCurve())
			dest.setLod2MultiCurve(jaxb.getGMLMarshaller().marshalMultiCurveProperty(src.getLod2MultiCurve()));

		if (src.isSetLod3MultiCurve())
			dest.setLod3MultiCurve(jaxb.getGMLMarshaller().marshalMultiCurveProperty(src.getLod3MultiCurve()));

		if (src.isSetLod4MultiCurve())
			dest.setLod4MultiCurve(jaxb.getGMLMarshaller().marshalMultiCurveProperty(src.getLod4MultiCurve()));	

		if (src.isSetOuterBuildingInstallation()) {
			for (BuildingInstallationProperty buildingInstallationProperty : src.getOuterBuildingInstallation())
				dest.getOuterBuildingInstallation().add(marshalBuildingInstallationProperty(buildingInstallationProperty));
		}

		if (src.isSetInteriorBuildingInstallation()) {
			for (IntBuildingInstallationProperty intBuildingInstallationProperty : src.getInteriorBuildingInstallation())
				dest.getInteriorBuildingInstallation().add(marshalIntBuildingInstallationProperty(intBuildingInstallationProperty));
		}

		if (src.isSetBoundedBySurface()) {
			for (BoundarySurfaceProperty boundarySurfaceProperty : src.getBoundedBySurface())
				dest.getBoundedBySurface().add(marshalBoundarySurfaceProperty(boundarySurfaceProperty));
		}

		if (src.isSetConsistsOfBuildingPart()) {
			for (BuildingPartProperty buildingPartProperty : src.getConsistsOfBuildingPart())
				dest.getConsistsOfBuildingPart().add(marshalBuildingPartProperty(buildingPartProperty));
		}

		if (src.isSetInteriorRoom()) {
			for (InteriorRoomProperty interiorRoomProperty : src.getInteriorRoom())
				dest.getInteriorRoom().add(marshalInteriorRoomProperty(interiorRoomProperty));
		}

		if (src.isSetAddress()) {
			for (AddressProperty addressProperty : src.getAddress())
				dest.getAddress().add(citygml.getCore100Marshaller().marshalAddressProperty(addressProperty));
		}	

		if (src.isSetGenericApplicationPropertyOfAbstractBuilding()) {
			for (ADEComponent adeComponent :src.getGenericApplicationPropertyOfAbstractBuilding())
				if (adeComponent.isSetContent())
					dest.get_GenericApplicationPropertyOfAbstractBuilding().add(citygml.ade2jaxbElement(adeComponent));
		}
	}

	public void marshalAbstractBoundarySurface(AbstractBoundarySurface src, AbstractBoundarySurfaceType dest) {
		citygml.getCore100Marshaller().marshalAbstractCityObject(src, dest);

		if (src.isSetLod2MultiSurface())
			dest.setLod2MultiSurface(jaxb.getGMLMarshaller().marshalMultiSurfaceProperty(src.getLod2MultiSurface()));

		if (src.isSetLod3MultiSurface())
			dest.setLod3MultiSurface(jaxb.getGMLMarshaller().marshalMultiSurfaceProperty(src.getLod3MultiSurface()));

		if (src.isSetLod4MultiSurface())
			dest.setLod4MultiSurface(jaxb.getGMLMarshaller().marshalMultiSurfaceProperty(src.getLod4MultiSurface()));

		if (src.isSetOpening()) {
			for (OpeningProperty openingProperty : src.getOpening())
				dest.getOpening().add(marshalOpeningProperty(openingProperty));
		}

		if (src.isSetGenericApplicationPropertyOfBoundarySurface()) {
			for (ADEComponent adeComponent :src.getGenericApplicationPropertyOfBoundarySurface())
				if (adeComponent.isSetContent())
					dest.get_GenericApplicationPropertyOfBoundarySurface().add(citygml.ade2jaxbElement(adeComponent));
		}
	}

	public void marshalAbstractOpening(AbstractOpening src, AbstractOpeningType dest) {
		citygml.getCore100Marshaller().marshalAbstractCityObject(src, dest);

		if (src.isSetLod3MultiSurface())
			dest.setLod3MultiSurface(jaxb.getGMLMarshaller().marshalMultiSurfaceProperty(src.getLod3MultiSurface()));

		if (src.isSetLod4MultiSurface())
			dest.setLod4MultiSurface(jaxb.getGMLMarshaller().marshalMultiSurfaceProperty(src.getLod4MultiSurface()));

		if (src.isSetGenericApplicationPropertyOfOpening()) {
			for (ADEComponent adeComponent :src.getGenericApplicationPropertyOfOpening())
				if (adeComponent.isSetContent())
					dest.get_GenericApplicationPropertyOfOpening().add(citygml.ade2jaxbElement(adeComponent));
		}
	}

	public BoundarySurfacePropertyType marshalBoundarySurfaceProperty(BoundarySurfaceProperty src) {
		BoundarySurfacePropertyType dest = bldg.createBoundarySurfacePropertyType();
		jaxb.getGMLMarshaller().marshalFeatureProperty(src, dest);

		if (src.isSetBoundarySurface()) {
			JAXBElement<?> elem = jaxb.marshalJAXBElement(src.getBoundarySurface());
			if (elem != null && elem.getValue() instanceof AbstractBoundarySurfaceType)
				dest.set_Object((JAXBElement<?>)elem);
		}

		return dest;
	}

	public void marshalBuilding(Building src, BuildingType dest) {
		marshalAbstractBuilding(src, dest);

		if (src.isSetGenericApplicationPropertyOfBuilding()) {
			for (ADEComponent adeComponent :src.getGenericApplicationPropertyOfBuilding())
				if (adeComponent.isSetContent())
					dest.get_GenericApplicationPropertyOfBuilding().add(citygml.ade2jaxbElement(adeComponent));
		}
	}

	public BuildingType marshalBuilding(Building src) {
		BuildingType dest = bldg.createBuildingType();
		marshalBuilding(src, dest);

		return dest;
	}

	public void marshalBuildingFurniture(BuildingFurniture src, BuildingFurnitureType dest) {
		citygml.getCore100Marshaller().marshalAbstractCityObject(src, dest);

		if (src.isSetClazz())
			dest.setClazz(src.getClazz().getValue());

		if (src.isSetFunction()) {
			for (Code function : src.getFunction())
				dest.getFunction().add(function.getValue());
		}

		if (src.isSetUsage()) {
			for (Code usage : src.getUsage())
				dest.getUsage().add(usage.getValue());
		}

		if (src.isSetLod4Geometry())
			dest.setLod4Geometry(jaxb.getGMLMarshaller().marshalGeometryProperty(src.getLod4Geometry()));

		if (src.isSetLod4ImplicitRepresentation())
			dest.setLod4ImplicitRepresentation(citygml.getCore100Marshaller().marshalImplicitRepresentationProperty(src.getLod4ImplicitRepresentation()));

		if (src.isSetGenericApplicationPropertyOfBuildingFurniture()) {
			for (ADEComponent adeComponent :src.getGenericApplicationPropertyOfBuildingFurniture())
				if (adeComponent.isSetContent())
					dest.get_GenericApplicationPropertyOfBuildingFurniture().add(citygml.ade2jaxbElement(adeComponent));
		}
	}

	public BuildingFurnitureType marshalBuildingFurniture(BuildingFurniture src) {
		BuildingFurnitureType dest = bldg.createBuildingFurnitureType();
		marshalBuildingFurniture(src, dest);

		return dest;
	}

	public void marshalBuildingInstallation(BuildingInstallation src, BuildingInstallationType dest) {
		citygml.getCore100Marshaller().marshalAbstractCityObject(src, dest);

		if (src.isSetClazz())
			dest.setClazz(src.getClazz().getValue());

		if (src.isSetFunction()) {
			for (Code function : src.getFunction())
				dest.getFunction().add(function.getValue());
		}

		if (src.isSetUsage()) {
			for (Code usage : src.getUsage())
				dest.getUsage().add(usage.getValue());
		}

		if (src.isSetLod2Geometry())
			dest.setLod2Geometry(jaxb.getGMLMarshaller().marshalGeometryProperty(src.getLod2Geometry()));

		if (src.isSetLod3Geometry())
			dest.setLod3Geometry(jaxb.getGMLMarshaller().marshalGeometryProperty(src.getLod3Geometry()));

		if (src.isSetLod4Geometry())
			dest.setLod4Geometry(jaxb.getGMLMarshaller().marshalGeometryProperty(src.getLod4Geometry()));

		if (src.isSetGenericApplicationPropertyOfBuildingInstallation()) {
			for (ADEComponent adeComponent :src.getGenericApplicationPropertyOfBuildingInstallation())
				if (adeComponent.isSetContent())
					dest.get_GenericApplicationPropertyOfBuildingInstallation().add(citygml.ade2jaxbElement(adeComponent));
		}

	}

	public BuildingInstallationType marshalBuildingInstallation(BuildingInstallation src) {
		BuildingInstallationType dest = bldg.createBuildingInstallationType();
		marshalBuildingInstallation(src, dest);

		return dest;
	}

	public BuildingInstallationPropertyType marshalBuildingInstallationProperty(BuildingInstallationProperty src) {
		BuildingInstallationPropertyType dest = bldg.createBuildingInstallationPropertyType();
		jaxb.getGMLMarshaller().marshalFeatureProperty(src, dest);

		if (src.isSetBuildingInstallation()) {
			JAXBElement<?> elem = jaxb.marshalJAXBElement(src.getBuildingInstallation());
			if (elem != null && elem.getValue() instanceof BuildingInstallationType)
				dest.set_Object((JAXBElement<?>)elem);
		}

		return dest;
	}

	public void marshalBuildingPart(BuildingPart src, BuildingPartType dest) {
		marshalAbstractBuilding(src, dest);

		if (src.isSetGenericApplicationPropertyOfBuildingPart()) {
			for (ADEComponent adeComponent :src.getGenericApplicationPropertyOfBuildingPart())
				if (adeComponent.isSetContent())
					dest.get_GenericApplicationPropertyOfBuildingPart().add(citygml.ade2jaxbElement(adeComponent));
		}
	}

	public BuildingPartType marshalBuildingPart(BuildingPart src) {
		BuildingPartType dest = bldg.createBuildingPartType();
		marshalBuildingPart(src, dest);

		return dest;
	}

	public BuildingPartPropertyType marshalBuildingPartProperty(BuildingPartProperty src) {
		BuildingPartPropertyType dest = bldg.createBuildingPartPropertyType();
		jaxb.getGMLMarshaller().marshalFeatureProperty(src, dest);

		if (src.isSetBuildingPart()) {
			JAXBElement<?> elem = jaxb.marshalJAXBElement(src.getBuildingPart());
			if (elem != null && elem.getValue() instanceof BuildingPartType)
				dest.set_Object((JAXBElement<?>)elem);
		}

		return dest;
	}

	public void marshalCeilingSurface(CeilingSurface src, CeilingSurfaceType dest) {
		marshalAbstractBoundarySurface(src, dest);

		if (src.isSetGenericApplicationPropertyOfCeilingSurface()) {
			for (ADEComponent adeComponent :src.getGenericApplicationPropertyOfCeilingSurface())
				if (adeComponent.isSetContent())
					dest.get_GenericApplicationPropertyOfCeilingSurface().add(citygml.ade2jaxbElement(adeComponent));
		}
	}

	public CeilingSurfaceType marshalCeilingSurface(CeilingSurface src) {
		CeilingSurfaceType dest = bldg.createCeilingSurfaceType();
		marshalCeilingSurface(src, dest);

		return dest;
	}

	public void marshalClosureSurface(ClosureSurface src, ClosureSurfaceType dest) {
		marshalAbstractBoundarySurface(src, dest);

		if (src.isSetGenericApplicationPropertyOfClosureSurface()) {
			for (ADEComponent adeComponent :src.getGenericApplicationPropertyOfClosureSurface())
				if (adeComponent.isSetContent())
					dest.get_GenericApplicationPropertyOfClosureSurface().add(citygml.ade2jaxbElement(adeComponent));
		}
	}

	public ClosureSurfaceType marshalClosureSurface(ClosureSurface src) {
		ClosureSurfaceType dest = bldg.createClosureSurfaceType();
		marshalClosureSurface(src, dest);

		return dest;
	}

	public void marshalDoor(Door src, DoorType dest) {
		marshalAbstractOpening(src, dest);

		if (src.isSetAddress()) {
			for (AddressProperty addressProperty : src.getAddress())
				dest.getAddress().add(citygml.getCore100Marshaller().marshalAddressProperty(addressProperty));
		}

		if (src.isSetGenericApplicationPropertyOfDoor()) {
			for (ADEComponent adeComponent :src.getGenericApplicationPropertyOfDoor())
				if (adeComponent.isSetContent())
					dest.get_GenericApplicationPropertyOfDoor().add(citygml.ade2jaxbElement(adeComponent));
		}
	}

	public DoorType marshalDoor(Door src) {
		DoorType dest = bldg.createDoorType();
		marshalDoor(src, dest);

		return dest;
	}

	public void marshalFloorSurface(FloorSurface src, FloorSurfaceType dest) {
		marshalAbstractBoundarySurface(src, dest);

		if (src.isSetGenericApplicationPropertyOfFloorSurface()) {
			for (ADEComponent adeComponent :src.getGenericApplicationPropertyOfFloorSurface())
				if (adeComponent.isSetContent())
					dest.get_GenericApplicationPropertyOfFloorSurface().add(citygml.ade2jaxbElement(adeComponent));
		}
	}

	public FloorSurfaceType marshalFloorSurface(FloorSurface src) {
		FloorSurfaceType dest = bldg.createFloorSurfaceType();
		marshalFloorSurface(src, dest);

		return dest;
	}

	public void marshalGroundSurface(GroundSurface src, GroundSurfaceType dest) {
		marshalAbstractBoundarySurface(src, dest);

		if (src.isSetGenericApplicationPropertyOfGroundSurface()) {
			for (ADEComponent adeComponent :src.getGenericApplicationPropertyOfGroundSurface())
				if (adeComponent.isSetContent())
					dest.get_GenericApplicationPropertyOfGroundSurface().add(citygml.ade2jaxbElement(adeComponent));
		}
	}

	public GroundSurfaceType marshalGroundSurface(GroundSurface src) {
		GroundSurfaceType dest = bldg.createGroundSurfaceType();
		marshalGroundSurface(src, dest);

		return dest;
	}
	
	public void marshalOuterCeilingSurface(OuterCeilingSurface src, WallSurfaceType dest) {
		marshalAbstractBoundarySurface(src, dest);

		if (src.isSetGenericApplicationPropertyOfOuterCeilingSurface()) {
			for (ADEComponent adeComponent :src.getGenericApplicationPropertyOfOuterCeilingSurface())
				if (adeComponent.isSetContent())
					dest.get_GenericApplicationPropertyOfWallSurface().add(citygml.ade2jaxbElement(adeComponent));
		}
	}

	public WallSurfaceType marshalOuterCeilingSurface(OuterCeilingSurface src) {
		WallSurfaceType dest = bldg.createWallSurfaceType();
		marshalOuterCeilingSurface(src, dest);

		return dest;
	}
	
	public void marshalOuterFloorSurface(OuterFloorSurface src, WallSurfaceType dest) {
		marshalAbstractBoundarySurface(src, dest);

		if (src.isSetGenericApplicationPropertyOfOuterFloorSurface()) {
			for (ADEComponent adeComponent :src.getGenericApplicationPropertyOfOuterFloorSurface())
				if (adeComponent.isSetContent())
					dest.get_GenericApplicationPropertyOfWallSurface().add(citygml.ade2jaxbElement(adeComponent));
		}
	}

	public WallSurfaceType marshalOuterFloorSurface(OuterFloorSurface src) {
		WallSurfaceType dest = bldg.createWallSurfaceType();
		marshalOuterFloorSurface(src, dest);

		return dest;
	}

	public void marshalIntBuildingInstallation(IntBuildingInstallation src, IntBuildingInstallationType dest) {
		citygml.getCore100Marshaller().marshalAbstractCityObject(src, dest);

		if (src.isSetClazz())
			dest.setClazz(src.getClazz().getValue());

		if (src.isSetFunction()) {
			for (Code function : src.getFunction())
				dest.getFunction().add(function.getValue());
		}

		if (src.isSetUsage()) {
			for (Code usage : src.getUsage())
				dest.getUsage().add(usage.getValue());
		}

		if (src.isSetLod4Geometry())
			dest.setLod4Geometry(jaxb.getGMLMarshaller().marshalGeometryProperty(src.getLod4Geometry()));

		if (src.isSetGenericApplicationPropertyOfIntBuildingInstallation()) {
			for (ADEComponent adeComponent :src.getGenericApplicationPropertyOfIntBuildingInstallation())
				if (adeComponent.isSetContent())
					dest.get_GenericApplicationPropertyOfIntBuildingInstallation().add(citygml.ade2jaxbElement(adeComponent));
		}
	}

	public IntBuildingInstallationType marshalIntBuildingInstallation(IntBuildingInstallation src) {
		IntBuildingInstallationType dest = bldg.createIntBuildingInstallationType();
		marshalIntBuildingInstallation(src, dest);

		return dest;
	}

	public IntBuildingInstallationPropertyType marshalIntBuildingInstallationProperty(IntBuildingInstallationProperty src) {
		IntBuildingInstallationPropertyType dest = bldg.createIntBuildingInstallationPropertyType();
		jaxb.getGMLMarshaller().marshalFeatureProperty(src, dest);

		if (src.isSetIntBuildingInstallation()) {
			JAXBElement<?> elem = jaxb.marshalJAXBElement(src.getIntBuildingInstallation());
			if (elem != null && elem.getValue() instanceof IntBuildingInstallationType)
				dest.set_Object((JAXBElement<?>)elem);
		}

		return dest;
	}

	public InteriorFurniturePropertyType marshalInteriorFurnitureProperty(InteriorFurnitureProperty src) {
		InteriorFurniturePropertyType dest = bldg.createInteriorFurniturePropertyType();
		jaxb.getGMLMarshaller().marshalFeatureProperty(src, dest);

		if (src.isSetBuildingFurniture()) {
			JAXBElement<?> elem = jaxb.marshalJAXBElement(src.getBuildingFurniture());
			if (elem != null && elem.getValue() instanceof BuildingFurnitureType)
				dest.set_Object((JAXBElement<?>)elem);
		}

		return dest;
	}

	public InteriorRoomPropertyType marshalInteriorRoomProperty(InteriorRoomProperty src) {
		InteriorRoomPropertyType dest = bldg.createInteriorRoomPropertyType();
		jaxb.getGMLMarshaller().marshalFeatureProperty(src, dest);

		if (src.isSetRoom()) {
			JAXBElement<?> elem = jaxb.marshalJAXBElement(src.getRoom());
			if (elem != null && elem.getValue() instanceof RoomType)
				dest.set_Object((JAXBElement<?>)elem);
		}

		return dest;
	}

	public void marshalInteriorWallSurface(InteriorWallSurface src, InteriorWallSurfaceType dest) {
		marshalAbstractBoundarySurface(src, dest);

		if (src.isSetGenericApplicationPropertyOfInteriorWallSurface()) {
			for (ADEComponent adeComponent :src.getGenericApplicationPropertyOfInteriorWallSurface())
				if (adeComponent.isSetContent())
					dest.get_GenericApplicationPropertyOfInteriorWallSurface().add(citygml.ade2jaxbElement(adeComponent));
		}
	}

	public InteriorWallSurfaceType marshalInteriorWallSurface(InteriorWallSurface src) {
		InteriorWallSurfaceType dest = bldg.createInteriorWallSurfaceType();
		marshalInteriorWallSurface(src, dest);

		return dest;
	}

	public OpeningPropertyType marshalOpeningProperty(OpeningProperty src) {
		OpeningPropertyType dest = bldg.createOpeningPropertyType();
		jaxb.getGMLMarshaller().marshalFeatureProperty(src, dest);

		if (src.isSetOpening()) {
			JAXBElement<?> elem = jaxb.marshalJAXBElement(src.getOpening());
			if (elem != null && elem.getValue() instanceof AbstractOpeningType)
				dest.set_Object((JAXBElement<?>)elem);
		}

		return dest;
	}

	public void marshalRoofSurface(RoofSurface src, RoofSurfaceType dest) {
		marshalAbstractBoundarySurface(src, dest);

		if (src.isSetGenericApplicationPropertyOfRoofSurface()) {
			for (ADEComponent adeComponent :src.getGenericApplicationPropertyOfRoofSurface())
				if (adeComponent.isSetContent())
					dest.get_GenericApplicationPropertyOfRoofSurface().add(citygml.ade2jaxbElement(adeComponent));
		}
	}

	public RoofSurfaceType marshalRoofSurface(RoofSurface src) {
		RoofSurfaceType dest = bldg.createRoofSurfaceType();
		marshalRoofSurface(src, dest);

		return dest;
	}

	public void marshalRoom(Room src, RoomType dest) {
		citygml.getCore100Marshaller().marshalAbstractCityObject(src, dest);

		if (src.isSetClazz())
			dest.setClazz(src.getClazz().getValue());

		if (src.isSetFunction()) {
			for (Code function : src.getFunction())
				dest.getFunction().add(function.getValue());
		}

		if (src.isSetUsage()) {
			for (Code usage : src.getUsage())
				dest.getUsage().add(usage.getValue());
		}

		if (src.isSetLod4Solid())
			dest.setLod4Solid(jaxb.getGMLMarshaller().marshalSolidProperty(src.getLod4Solid()));

		if (src.isSetLod4MultiSurface())
			dest.setLod4MultiSurface(jaxb.getGMLMarshaller().marshalMultiSurfaceProperty(src.getLod4MultiSurface()));

		if (src.isSetBoundedBySurface()) {
			for (BoundarySurfaceProperty boundarySurfaceProperty : src.getBoundedBySurface())
				dest.getBoundedBySurface().add(marshalBoundarySurfaceProperty(boundarySurfaceProperty));
		}

		if (src.isSetInteriorFurniture()) {
			for (InteriorFurnitureProperty interiorFurnitureProperty : src.getInteriorFurniture())
				dest.getInteriorFurniture().add(marshalInteriorFurnitureProperty(interiorFurnitureProperty));
		}

		if (src.isSetRoomInstallation()) {
			for (IntBuildingInstallationProperty intBuildingInstallationProperty : src.getRoomInstallation())
				dest.getRoomInstallation().add(marshalIntBuildingInstallationProperty(intBuildingInstallationProperty));
		}	

		if (src.isSetGenericApplicationPropertyOfRoom()) {
			for (ADEComponent adeComponent :src.getGenericApplicationPropertyOfRoom())
				if (adeComponent.isSetContent())
					dest.get_GenericApplicationPropertyOfRoom().add(citygml.ade2jaxbElement(adeComponent));
		}
	}

	public RoomType marshalRoom(Room src) {
		RoomType dest = bldg.createRoomType();
		marshalRoom(src, dest);

		return dest;
	}

	public void marshalWallSurface(WallSurface src, WallSurfaceType dest) {
		marshalAbstractBoundarySurface(src, dest);

		if (src.isSetGenericApplicationPropertyOfWallSurface()) {
			for (ADEComponent adeComponent :src.getGenericApplicationPropertyOfWallSurface())
				if (adeComponent.isSetContent())
					dest.get_GenericApplicationPropertyOfWallSurface().add(citygml.ade2jaxbElement(adeComponent));
		}
	}

	public WallSurfaceType marshalWallSurface(WallSurface src) {
		WallSurfaceType dest = bldg.createWallSurfaceType();
		marshalWallSurface(src, dest);

		return dest;
	}

	public void marshalWindow(Window src, WindowType dest) {
		marshalAbstractOpening(src, dest);

		if (src.isSetGenericApplicationPropertyOfWindow()) {
			for (ADEComponent adeComponent :src.getGenericApplicationPropertyOfWindow())
				if (adeComponent.isSetContent())
					dest.get_GenericApplicationPropertyOfWindow().add(citygml.ade2jaxbElement(adeComponent));
		}
	}

	public WindowType marshalWindow(Window src) {
		WindowType dest = bldg.createWindowType();
		marshalWindow(src, dest);

		return dest;
	}

}
