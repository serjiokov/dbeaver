package org.jkiss.dbeaver.tools.transfer.stream.csv;

import org.jkiss.dbeaver.model.DBPDataSource;
import org.jkiss.dbeaver.model.data.DBDDataFilter;
import org.jkiss.dbeaver.model.data.DBDDataReceiver;
import org.jkiss.dbeaver.model.exec.DBCException;
import org.jkiss.dbeaver.model.exec.DBCExecutionSource;
import org.jkiss.dbeaver.model.exec.DBCSession;
import org.jkiss.dbeaver.model.exec.DBCStatistics;
import org.jkiss.dbeaver.model.struct.DBSDataContainer;
import org.jkiss.dbeaver.model.struct.DBSObject;

public class CsvInputDataSource  implements DBSDataContainer{

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DBSObject getParentObject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isPersisted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public DBPDataSource getDataSource() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getSupportedFeatures() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public DBCStatistics readData(DBCExecutionSource source, DBCSession session, DBDDataReceiver dataReceiver,
			DBDDataFilter dataFilter, long firstRow, long maxRows, long flags) throws DBCException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long countData(DBCExecutionSource source, DBCSession session, DBDDataFilter dataFilter) throws DBCException {
		// TODO Auto-generated method stub
		return 0;
	}

}
