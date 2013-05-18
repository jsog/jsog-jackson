package com.github.stickfigure.jsog;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * This object lets us persuade jackson into serializing JSOG structures. It is the id generated,
 * and a special serializer knows how to convert it to an @id or @ref as appropriate.
 *
 * @author Jeff Schnitzer <jeff@infohazard.org>
 */
@JsonSerialize(using=JSOGRefSerializer.class)
public class JSOGRef
{
	/** The stringified numeric */
	public String ref;

	/**
	 * A flag we use to determine if this ref has already been serialized. Because jackson calls the same
	 * code for serializing both ids and refs, we simply assume the first use is an id and all subsequent
	 * uses are refs.
	 */
	public transient boolean used;

	/** */
	public JSOGRef(int val) {
		ref = Integer.toString(val);
	}
}