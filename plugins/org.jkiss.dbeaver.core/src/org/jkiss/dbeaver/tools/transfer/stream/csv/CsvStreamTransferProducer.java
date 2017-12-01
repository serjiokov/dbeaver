package org.jkiss.dbeaver.tools.transfer.stream.csv;

import java.io.File;

import org.jkiss.code.NotNull;
import org.jkiss.dbeaver.DBException;
import org.jkiss.dbeaver.model.runtime.DBRProgressMonitor;
import org.jkiss.dbeaver.model.struct.DBSDataContainer;
import org.jkiss.dbeaver.model.struct.DBSObject;
import org.jkiss.dbeaver.tools.transfer.IDataTransferConsumer;
import org.jkiss.dbeaver.tools.transfer.IDataTransferProducer;
import org.jkiss.dbeaver.tools.transfer.database.DatabaseProducerSettings;

public class CsvStreamTransferProducer implements IDataTransferProducer<DatabaseProducerSettings> {

	@NotNull
	private CsvInputDataSource dataContainer;
	private CsvDataTransferSettings settings;

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
			DatabaseProducerSettings settings) throws DBException {
	}

	public String toString() {
		return String.format("%s-%s ", CsvStreamTransferProducer.class.getName());
	}

}
