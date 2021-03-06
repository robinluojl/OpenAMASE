// ===============================================================================
// Authors: AFRL/RQQD
// Organization: Air Force Research Laboratory, Aerospace Systems Directorate, Power and Control Division
// 
// Copyright (c) 2017 Government of the United State of America, as represented by
// the Secretary of the Air Force.  No copyright is claimed in the United States under
// Title 17, U.S. Code.  All Other Rights Reserved.
// ===============================================================================

/*
 * GRAL: GRAphing Library for Java(R)
 *
 * (C) Copyright 2009-2013 Erich Seifert <dev[at]erichseifert.de>,
 * Michael Seifert <mseifert[at]error-reports.org>
 *
 * This file is part of GRAL.
 *
 * GRAL is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * GRAL is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with GRAL.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.erichseifert.gral.plots.colors;

import java.awt.Paint;

import de.erichseifert.gral.util.MathUtils;

/**
 * Class that maps integer numbers to Paint objects. This can be used to
 * generate colors or gradients for various elements in a plot, e.g. lines,
 * areas, etc.
 */
public abstract class IndexedColorMapper
		extends AbstractColorMapper<Integer> {
	/** Version id for serialization. */
	private static final long serialVersionUID = 553890535328678411L;

	/**
	 * Returns the Paint object according to the specified index.
	 * @param value Numeric index.
	 * @return Paint object.
	 */
	public abstract Paint get(int value);

	/**
	 * Returns the Paint object according to the specified index. The specified
	 * value will be handled like an integer index.
	 * @param index Numeric index object.
	 * @return Paint object.
	 */
	public Paint get(Number index) {
		return get(index.intValue());
	}

	@Override
	protected Integer applyMode(Integer index, Integer rangeMin, Integer rangeMax) {
		if (index.intValue() >= rangeMin.intValue() &&
				index.intValue() <= rangeMax.intValue()) {
			return index;
		}
		Mode mode = getMode();
		if (mode == Mode.REPEAT) {
			return MathUtils.limit(index, rangeMin, rangeMax);
		} else if (mode == Mode.CIRCULAR) {
			int range = rangeMax.intValue() - rangeMin.intValue() + 1;
			int i = index.intValue()%range;
			if (i < 0) {
				i += range;
			}
			return i + rangeMin.intValue();
		}
		return null;
	}
}
