package org.quick.meduo.tools.bloomfilter.filter;

import org.quick.meduo.tools.core.util.HashUtil;

public class RSFilter extends AbstractFilter {
	private static final long serialVersionUID = 1L;

	public RSFilter(long maxValue, int machineNum) {
		super(maxValue, machineNum);
	}

	public RSFilter(long maxValue) {
		super(maxValue);
	}

	@Override
	public long hash(String str) {
		return HashUtil.rsHash(str) % size;
	}

}
