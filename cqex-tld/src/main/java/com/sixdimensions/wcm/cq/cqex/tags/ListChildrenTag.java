/*
 * Copyright 2012 - Six Dimensions
 */
package com.sixdimensions.wcm.cq.cqex.tags;

import org.apache.sling.api.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tldgen.annotations.Attribute;
import org.tldgen.annotations.BodyContent;
import org.tldgen.annotations.Tag;

import com.sixdimensions.wcm.cq.cqex.util.IterableIterator;

/**
 * Tag for listing the children of a resource. The children are saved into the
 * page context as a variable with the name var and are saved as an
 * IterableIterator, which means they can be iterated through JSTL.
 * 
 * @author dklco
 */
@Tag(bodyContent = BodyContent.EMPTY, example = "&lt;cqex:listChildren var=\"resources\" resource=\"${resource}\" />")
public class ListChildrenTag extends AttributeSettingTag {
	private static final Logger log = LoggerFactory
			.getLogger(ListChildrenTag.class);
	private static final long serialVersionUID = 5861756752614447760L;

	/**
	 * The resource to list the children of, required but may be null
	 */
	@Attribute(required = true)
	private Resource resource;

	/**
	 * The page context variable in which to save the children
	 */
	@Attribute
	private final String var = "resources";

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.jsp.tagext.TagSupport#doEndTag()
	 */
	@Override
	public int doEndTag() {
		log.trace("doEndTag");
		if (this.resource != null) {
			log.debug("Listing children of: " + this.resource.getPath());
			IterableIterator<Resource> children = new IterableIterator<Resource>(
					this.resource.listChildren());
			this.setAttribute(this.var, children);
		} else {
			log.debug("No/null resource specified");
		}
		return javax.servlet.jsp.tagext.Tag.EVAL_PAGE;
	}

	/**
	 * Get the current resource.
	 * 
	 * @return the current resource
	 */
	public Resource getResource() {
		return this.resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}
}
