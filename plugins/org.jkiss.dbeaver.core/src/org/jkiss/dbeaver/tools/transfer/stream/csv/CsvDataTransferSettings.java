package org.jkiss.dbeaver.tools.transfer.stream.csv;

import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.operation.IRunnableContext;
import org.jkiss.dbeaver.tools.transfer.IDataTransferSettings;
import org.jkiss.dbeaver.tools.transfer.database.DatabaseProducerSettings.ExtractType;
import org.jkiss.dbeaver.tools.transfer.wizard.DataTransferSettings;

public class CsvDataTransferSettings  implements IDataTransferSettings {

	@Override
	public void loadSettings(IRunnableContext runnableContext, DataTransferSettings dataTransferSettings,
			IDialogSettings dialogSettings) {
		
		if (dialogSettings.get("delimiter") != null) {
			System.out.println();
        }
		
	}

	@Override
	public void saveSettings(IDialogSettings dialogSettings) {
		// TODO Auto-generated method stub
		System.out.println();
	}

}
