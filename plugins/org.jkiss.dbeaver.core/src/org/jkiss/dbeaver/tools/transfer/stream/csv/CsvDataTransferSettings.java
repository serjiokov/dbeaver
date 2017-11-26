package org.jkiss.dbeaver.tools.transfer.stream.csv;

import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.operation.IRunnableContext;
import org.jkiss.dbeaver.tools.transfer.IDataTransferSettings;
import org.jkiss.dbeaver.tools.transfer.database.DatabaseProducerSettings.ExtractType;
import org.jkiss.dbeaver.tools.transfer.wizard.DataTransferSettings;

public class CsvDataTransferSettings  implements IDataTransferSettings {

	private static final String DELIMITER = "delimiter";
	@Override
	public void loadSettings(IRunnableContext runnableContext, DataTransferSettings dataTransferSettings,
			IDialogSettings dialogSettings) {
		
		
		String delimiter = dialogSettings.get(DELIMITER);
		if (delimiter != null) {
			System.out.println("Delimiter: " + delimiter);
        }
		
	}

	@Override
	public void saveSettings(IDialogSettings dialogSettings) {
		 dialogSettings.put(DELIMITER, "1111");
	}

}
