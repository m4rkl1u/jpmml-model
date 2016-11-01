/*
 * Copyright (c) 2016 Villu Ruusmann
 */
package org.jpmml.model;

import org.jpmml.schema.Version;
import org.xml.sax.XMLReader;

/**
 * <p>
 * A SAX filter that skips a PMML element and its contents.
 * </p>
 */
public class ElementFilter extends SkipFilter {

	private String namespaceURI = null;

	private String localName = null;


	public ElementFilter(String localName){
		this((Version.PMML_4_3).getNamespaceURI(), localName);
	}

	public ElementFilter(String namespaceURI, String localName){
		setNamespaceURI(namespaceURI);
		setLocalName(localName);
	}

	public ElementFilter(XMLReader reader, String localName){
		this(reader, (Version.PMML_4_3).getNamespaceURI(), localName);
	}

	public ElementFilter(XMLReader reader, String namespaceURI, String localName){
		super(reader);

		setNamespaceURI(namespaceURI);
		setLocalName(localName);
	}

	@Override
	public boolean isSkipped(String namespaceURI, String localName){

		if((this.localName).equals(localName)){

			if(this.namespaceURI == null || (this.namespaceURI).equals(namespaceURI)){
				return true;
			}
		}

		return false;
	}

	public String getNamespaceURI(){
		return this.namespaceURI;
	}

	private void setNamespaceURI(String namespaceURI){
		this.namespaceURI = namespaceURI;
	}

	public String getLocalName(){
		return this.localName;
	}

	private void setLocalName(String localName){

		if(localName == null){
			throw new NullPointerException();
		}

		this.localName = localName;
	}
}