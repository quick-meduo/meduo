package org.quick.meduo.tools.core.text.escape;

import org.quick.meduo.tools.core.text.replacer.LookupReplacer;
import org.quick.meduo.tools.core.text.replacer.ReplacerChain;

/**
 * HTML4的UNESCAPE
 * 
 * @author looly
 *
 */
public class Html4Unescape extends ReplacerChain {
	private static final long serialVersionUID = 1L;
	
	protected static final String[][] BASIC_UNESCAPE  = InternalEscapeUtil.invert(Html4Escape.BASIC_ESCAPE);
	protected static final String[][] ISO8859_1_UNESCAPE  = InternalEscapeUtil.invert(Html4Escape.ISO8859_1_ESCAPE);
	protected static final String[][] HTML40_EXTENDED_UNESCAPE  = InternalEscapeUtil.invert(Html4Escape.HTML40_EXTENDED_ESCAPE);

	public Html4Unescape() {
		addChain(new LookupReplacer(BASIC_UNESCAPE));
		addChain(new LookupReplacer(ISO8859_1_UNESCAPE));
		addChain(new LookupReplacer(HTML40_EXTENDED_UNESCAPE));
		addChain(new NumericEntityUnescaper());
	}
}
