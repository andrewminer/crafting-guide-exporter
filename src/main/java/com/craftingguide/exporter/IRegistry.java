package com.craftingguide.exporter;

import com.craftingguide.exporter.IDumper;
import com.craftingguide.exporter.IGatherer;

public interface IRegistry {

	public void registerDumper(IDumper dumper);

	public void registerGatherer(IGatherer gatherer);
}
