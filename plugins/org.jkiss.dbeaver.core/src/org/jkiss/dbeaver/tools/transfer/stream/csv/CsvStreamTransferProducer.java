package org.jkiss.dbeaver.tools.transfer.stream.csv;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.jkiss.code.NotNull;
import org.jkiss.dbeaver.DBException;
import org.jkiss.dbeaver.model.runtime.DBRProgressMonitor;
import org.jkiss.dbeaver.model.struct.DBSObject;
import org.jkiss.dbeaver.tools.transfer.IDataTransferConsumer;
import org.jkiss.dbeaver.tools.transfer.IDataTransferProducer;

public class CsvStreamTransferProducer implements IDataTransferProducer<CsvDataTransferSettings> {

	private static final String BUNDLE_ID = "org.jkiss.dbeaver.tools.transfer.stream.csv";

	@NotNull
	private CsvInputDataSource dataContainer;

	
	public CsvStreamTransferProducer() {
	}

	public CsvStreamTransferProducer(@NotNull CsvInputDataSource dataContainer) {
		this.dataContainer = dataContainer;
	}

	@Override
	public DBSObject getSourceObject() {
		
		return dataContainer;
	}

	@Override
	public void transferData(DBRProgressMonitor monitor, IDataTransferConsumer consumer,
			CsvDataTransferSettings settings) throws DBException {

	}

	public String toString() {
		return String.format("%s-%s ", CsvStreamTransferProducer.class.getName());
	}

}
