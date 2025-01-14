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
package org.citygml4j.model.gml.feature;

import org.citygml4j.builder.copy.CopyBuilder;
import org.citygml4j.model.citygml.ade.ADEComponent;
import org.citygml4j.model.gml.GMLClass;
import org.citygml4j.model.gml.base.AssociationByRepOrRef;

public class FeatureProperty<T extends AbstractFeature> extends AssociationByRepOrRef<T> {
	private ADEComponent genericADEComponent;
	
	public FeatureProperty() {
		
	}
	
	public FeatureProperty(T feature) {
		super(feature);
	}
	
	public FeatureProperty(String href) {
		super(href);
	}
	
	public T getFeature() {
		return super.getObject();
	}

	public boolean isSetFeature() {
		return super.isSetObject();
	}

	public void setFeature(T feature) {
		super.setObject(feature);
	}

	public void unsetFeature() {
		super.unsetObject();
	}

	public ADEComponent getGenericADEComponent() {
		return genericADEComponent;
	}

	public boolean isSetGenericADEComponent() {
		return genericADEComponent != null;
	}
	
	public void setGenericADEComponent(ADEComponent genericADEComponent) {
		if (genericADEComponent != null)
			genericADEComponent.setParent(this);
		
		this.genericADEComponent = genericADEComponent;
	}
	
	public void unsetGenericADEComponent() {
		if (isSetGenericADEComponent())
			genericADEComponent.unsetParent();
		
		genericADEComponent = null;
	}

	public GMLClass getGMLClass() {
		return GMLClass.FEATURE_PROPERTY;
	}

	@SuppressWarnings("unchecked")
	public Class<T> getAssociableClass() {
		return (Class<T>)AbstractFeature.class;
	}

	@SuppressWarnings("unchecked")
	public Object copyTo(Object target, CopyBuilder copyBuilder) {
		FeatureProperty<T> copy = (target == null) ? new FeatureProperty<T>() : (FeatureProperty<T>)target;
		super.copyTo(copy, copyBuilder);
		
		if (isSetGenericADEComponent()) {
			copy.setGenericADEComponent((ADEComponent)copyBuilder.copy(genericADEComponent));
			if (copy.getGenericADEComponent() == genericADEComponent)
				genericADEComponent.setParent(this);
		}
		
		return copy;
	}

	public Object copy(CopyBuilder copyBuilder) {
		return copyTo(new FeatureProperty<T>(), copyBuilder);
	}

}
