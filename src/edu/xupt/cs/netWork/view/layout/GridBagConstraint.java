package edu.xupt.cs.netWork.view.layout;

import java.awt.GridBagConstraints;
import java.awt.Insets;

public class GridBagConstraint extends GridBagConstraints {
	private static final long serialVersionUID = 4151156536101477045L;
	
	public GridBagConstraint(int gridx, int gridy) {
		this(gridx, gridy, 1, 1);
	}

	public GridBagConstraint(int gridx, int gridy, double weightx, double weighty) {
		super();
		this.gridx = gridx;
		this.gridy = gridy;
		this.weightx = weightx;
		this.weighty = weighty;
		this.fill = GridBagConstraints.BOTH;
	}
	
	public GridBagConstraint setInsets(int top, int left, int bottom, int right) {
		this.insets = new Insets(top, left, bottom, right);
		return this;
	}
	
	public GridBagConstraint setIPad(int ipadx, int ipady) {
		this.ipadx = ipadx;
		this.ipady = ipady;
		
		return this;
	}
	
	public GridBagConstraint setGridLength(int gridwidth, int gridheight) {
		this.gridwidth = gridwidth;
		this.gridheight = gridheight;
		
		return this;
	}
	
	public GridBagConstraint setFill(int fill) {
		this.fill = fill;
		
		return this;
	}
	
	public GridBagConstraint setAnchor(int anchor) {
		this.anchor = anchor;
		
		return this;
	}
}
