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
package org.citygml4j.model.gml.xlink;

import org.citygml4j.builder.copy.CopyBuilder;
import org.citygml4j.model.common.base.ModelType;
import org.citygml4j.model.common.copy.Copyable;
import org.citygml4j.model.gml.GML;
import org.citygml4j.model.gml.GMLClass;

public enum XLinkType implements GML, Copyable {
	SIMPLE("simple"),
	EXTENDED("extended"),
	TITLE("title"),
	RESOURCE("resource"),
	LOCATOR("locator"),
	ARC("arc");

	private final String value;

	XLinkType(String v) {
		value = v;
	}

	public String getValue() {
		return value;
	}

	public static XLinkType fromValue(String v) {
		v = v.trim();

		for (XLinkType c: XLinkType.values())
			if (c.value.equals(v))
				return c;

		return SIMPLE;
	}

	public String toString() {
		return value;
	}
	
	public ModelType getModelType() {
		return ModelType.GML;
	}
	
	public GMLClass getGMLClass() {
		return GMLClass.XLINK_TYPE;
	}

	public Object copyTo(Object target, CopyBuilder copyBuilder) {
		return (target == null) ? SIMPLE : this;
	}
	
	public Object copy(CopyBuilder copyBuilder) {
		return this;
	}

}
