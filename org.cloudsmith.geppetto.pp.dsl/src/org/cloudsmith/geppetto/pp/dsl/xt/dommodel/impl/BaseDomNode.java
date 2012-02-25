/**
 * Copyright (c) 2012 Cloudsmith Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Cloudsmith
 * 
 */
package org.cloudsmith.geppetto.pp.dsl.xt.dommodel.impl;


import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.IDomNode;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.nodemodel.INode;

/**
 * A BaseDomNode is parented by an CompositeNode (or <code>null</node>).
 * 
 */
public abstract class BaseDomNode extends AbstractDomNode {

	/**
	 * Index in parent
	 */
	private int index;

	private int offset;

	private int length;

	private INode node;

	private EObject grammarElement;

	private String text;

	private EObject semanticElement;

	public void doLayout() {
		length = getText().length();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.geppetto.pp.dsl.xt.dommodel.impl.AbstractDomNode#getGrammarElement()
	 */
	@Override
	public EObject getGrammarElement() {
		return grammarElement;
	}

	int getIndex() {
		return index;
	}

	@Override
	public int getLength() {
		if(length == -1)
			getRootNode().doLayout();
		return length;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.geppetto.pp.dsl.xt.dommodel.impl.AbstractDomNode#getNode()
	 */
	@Override
	public INode getNode() {
		return node;
	}

	/**
	 * Returns a child node at the given index, or null if there is no such child.
	 * 
	 * @param index
	 * @return
	 */
	abstract IDomNode getNodeAt(int index);

	@Override
	public int getOffset() {
		if(offset == -1)
			getRootNode().doLayout();
		return offset;
	}

	private BaseDomNode getParentAsBaseDomNode() {
		IDomNode p = getParent();
		if(p == null)
			return null;
		if(p instanceof BaseDomNode == false)
			throw new IllegalStateException("A BaseDomNode must be parented by a BaseDomNode");
		return (BaseDomNode) p;
	}

	BaseDomNode getRootNode() {
		BaseDomNode x = this;
		while(x.getParent() != null)
			x = x.getParentAsBaseDomNode();
		return x;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.geppetto.pp.dsl.xt.dommodel.impl.AbstractDomNode#getSemanticElement()
	 */
	@Override
	public EObject getSemanticElement() {
		if(semanticElement != null)
			return semanticElement;
		return super.getSemanticElement();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.cloudsmith.geppetto.pp.dsl.xt.dommodel.impl.AbstractDomNode#getText()
	 */
	@Override
	public String getText() {
		return text;
	}

	void invalidateLayout() {
		if(!isLayoutValid())
			return; // already invalid
		offset = -1;
		length = -1;
		BaseDomNode p = getParentAsBaseDomNode();
		if(p != null)
			p.invalidateLayout();
	}

	public boolean isLayoutValid() {
		return offset != -1 && length != -1;
	}

	public void setGrammarElement(EObject grammarElement) {
		this.grammarElement = grammarElement;
	}

	void setIndex(int index) {
		this.index = index;
	}

	void setLength(int newLength) {
		this.length = newLength;
	}

	public void setNode(INode node) {
		this.node = node;
	}

	void setOffset(int newOffset) {
		this.offset = newOffset;
	}

	public void setSemanticElement(EObject semanticElement) {
		this.semanticElement = semanticElement;
	}

	public void setText(String text) {
		this.text = text;
	}

}
