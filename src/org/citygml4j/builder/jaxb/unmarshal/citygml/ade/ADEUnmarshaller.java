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
package org.citygml4j.builder.jaxb.unmarshal.citygml.ade;

import org.citygml4j.builder.jaxb.unmarshal.JAXBUnmarshaller;
import org.citygml4j.model.citygml.ade.ADEComponent;
import org.citygml4j.xml.io.reader.MissingADESchemaException;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public class ADEUnmarshaller {
	private final JAXBUnmarshaller jaxb;

	public ADEUnmarshaller(JAXBUnmarshaller jaxb) {
		this.jaxb = jaxb;
	}

	public ADEComponent unmarshal(Element element) throws MissingADESchemaException {
		if (jaxb.isParseSchema()) {
			try {
				jaxb.getSchemaHandler().parseSchema(element);
				jaxb.getSchemaHandler().parseSchema(element.getNamespaceURI(), null);
			} catch (SAXException e) {
				// 
			}
		}

		try {
			jaxb.getSchemaHandler().resolveAndParseSchema(element.getNamespaceURI());
		} catch (SAXException e) {
			// 
		} catch (MissingADESchemaException e) {
			if (jaxb.isThrowMissingADESchema())
				throw e;
		}

		return new ADEComponent(element);
	}

}
