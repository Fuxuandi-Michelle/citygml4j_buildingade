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
package org.citygml4j.model.citygml.relief;

import java.util.List;

import org.citygml4j.builder.copy.CopyBuilder;
import org.citygml4j.model.citygml.CityGMLClass;
import org.citygml4j.model.citygml.ade.ADEComponent;
import org.citygml4j.model.citygml.core.LodRepresentation;
import org.citygml4j.model.common.child.ChildList;
import org.citygml4j.model.common.visitor.FeatureFunctor;
import org.citygml4j.model.common.visitor.FeatureVisitor;
import org.citygml4j.model.common.visitor.GMLFunctor;
import org.citygml4j.model.common.visitor.GMLVisitor;
import org.citygml4j.model.gml.feature.BoundingShape;
import org.citygml4j.model.gml.geometry.AbstractGeometry;
import org.citygml4j.model.gml.geometry.GeometryProperty;
import org.citygml4j.model.gml.geometry.aggregates.MultiPointProperty;
import org.citygml4j.model.module.citygml.ReliefModule;

public class MassPointRelief extends AbstractReliefComponent {
	private MultiPointProperty reliefPoints;
	private List<ADEComponent> ade;

	public MassPointRelief() {

	}

	public MassPointRelief(ReliefModule module) {
		super(module);
	}

	public void addGenericApplicationPropertyOfMassPointRelief(ADEComponent ade) {
		if (this.ade == null)
			this.ade = new ChildList<ADEComponent>(this);

		this.ade.add(ade);
	}

	public List<ADEComponent> getGenericApplicationPropertyOfMassPointRelief() {
		if (ade == null)
			ade = new ChildList<ADEComponent>(this);

		return ade;
	}

	public MultiPointProperty getReliefPoints() {
		return reliefPoints;
	}

	public boolean isSetGenericApplicationPropertyOfMassPointRelief() {
		return ade != null && !ade.isEmpty();
	}

	public boolean isSetReliefPoints() {
		return reliefPoints != null;
	}

	public void setGenericApplicationPropertyOfMassPointRelief(List<ADEComponent> ade) {
		this.ade = new ChildList<ADEComponent>(this, ade);
	}

	public void setReliefPoints(MultiPointProperty reliefPoints) {
		if (reliefPoints != null)
			reliefPoints.setParent(this);

		this.reliefPoints = reliefPoints;
	}

	public void unsetGenericApplicationPropertyOfMassPointRelief() {
		if (isSetGenericApplicationPropertyOfMassPointRelief())
			ade.clear();

		ade = null;
	}

	public boolean unsetGenericApplicationPropertyOfMassPointRelief(ADEComponent ade) {
		return isSetGenericApplicationPropertyOfMassPointRelief() ? this.ade.remove(ade) : false;
	}

	public void unsetReliefPoints() {
		if (isSetReliefPoints())
			reliefPoints.unsetParent();

		reliefPoints = null;
	}

	public CityGMLClass getCityGMLClass() {
		return CityGMLClass.MASSPOINT_RELIEF;
	}

	@Override
	public BoundingShape calcBoundedBy(boolean setBoundedBy) {
		BoundingShape boundedBy = super.calcBoundedBy(false);
		if (boundedBy == null)
			boundedBy = new BoundingShape();

		if (isSetReliefPoints()) {
			if (reliefPoints.isSetMultiPoint()) {
				calcBoundedBy(boundedBy, reliefPoints.getMultiPoint());
			} else {
				// xlink
			}
		}

		if (boundedBy.isSetEnvelope()) {
			if (setBoundedBy)
				setBoundedBy(boundedBy);

			return boundedBy;
		} else
			return null;
	}

	public Object copy(CopyBuilder copyBuilder) {
		return copyTo(new MassPointRelief(), copyBuilder);
	}

	@Override
	public LodRepresentation getLodRepresentation() {
		LodRepresentation lodRepresentation = new LodRepresentation();

		if (isSetReliefPoints()) {
			List<GeometryProperty<? extends AbstractGeometry>> propertyList = lodRepresentation.getLodGeometry(getLod());
			if (propertyList != null)
				propertyList.add(reliefPoints);
		}

		return lodRepresentation;
	}

	@Override
	public Object copyTo(Object target, CopyBuilder copyBuilder) {
		MassPointRelief copy = (target == null) ? new MassPointRelief() : (MassPointRelief)target;
		super.copyTo(copy, copyBuilder);

		if (isSetReliefPoints()) {
			copy.setReliefPoints((MultiPointProperty)copyBuilder.copy(reliefPoints));
			if (copy.getReliefPoints() == reliefPoints)
				reliefPoints.setParent(this);
		}

		if (isSetGenericApplicationPropertyOfMassPointRelief()) {
			for (ADEComponent part : ade) {
				ADEComponent copyPart = (ADEComponent)copyBuilder.copy(part);
				copy.addGenericApplicationPropertyOfMassPointRelief(copyPart);

				if (part != null && copyPart == part)
					part.setParent(this);
			}
		}

		return copy;
	}

	public void accept(FeatureVisitor visitor) {
		visitor.visit(this);
	}

	public <T> T accept(FeatureFunctor<T> visitor) {
		return visitor.apply(this);
	}

	public void accept(GMLVisitor visitor) {
		visitor.visit(this);
	}

	public <T> T accept(GMLFunctor<T> visitor) {
		return visitor.apply(this);
	}

}
