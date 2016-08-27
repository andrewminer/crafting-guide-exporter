package com.craftingguide.exporter;

public interface IRegistry {

	public void registerDumper(IDumper dumper);
	
	public void registerEditor(IEditor editor);

	public void registerGatherer(IGatherer gatherer);
}
