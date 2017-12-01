package org.jkiss.dbeaver.tools.transfer.stream.csv;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.jkiss.dbeaver.core.CoreMessages;
import org.jkiss.dbeaver.registry.transfer.DataTransferProcessorDescriptor;
import org.jkiss.dbeaver.runtime.properties.PropertySourceCustom;
import org.jkiss.dbeaver.tools.transfer.wizard.DataTransferWizard;
import org.jkiss.dbeaver.ui.dialogs.ActiveWizardPage;
import org.jkiss.dbeaver.ui.properties.PropertyTreeViewer;

public class CsvProviderPageSettings extends ActiveWizardPage<DataTransferWizard> {

	private PropertyTreeViewer propsEditor;
	private PropertySourceCustom propertySource;
	private Text txtFilelocation;
	

	public CsvProviderPageSettings() {
		super(CoreMessages.data_transfer_wizard_settings_name);
		setTitle(CoreMessages.data_transfer_wizard_settings_title);
		setDescription(CoreMessages.data_transfer_wizard_settings_description);
		setPageComplete(false);
	}

	@Override
	public void createControl(Composite parent) {
		initializeDialogUnits(parent);

		Composite composite = new Composite(parent, SWT.NULL);

		composite.setLayout(new GridLayout(1, true));
		composite.setLayoutData(new GridData(GridData.FILL_BOTH, SWT.BORDER));

		Group generalSettings = new Group(composite, SWT.BORDER);
		generalSettings.setText(CoreMessages.data_transfer_wizard_settings_group_general);

		GridData dataTopGr = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		generalSettings.setLayoutData(dataTopGr);
		generalSettings.setLayout(new GridLayout(3, false));

		GridData lblData = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);

		GridData lblBtn = new GridData(SWT.END, SWT.FILL, false, false, 1, 1);
		lblBtn.heightHint = 18;

		GridData textData = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		textData.heightHint = 18;
		textData.widthHint = 200;

		CLabel lbl = new CLabel(generalSettings, SWT.NONE);
		lbl.setText(CoreMessages.import_csv_dialog_lbl_select_file);
		lbl.setLayoutData(lblData);

		txtFilelocation = new Text(generalSettings, SWT.NONE);
		txtFilelocation.setLayoutData(textData);
		txtFilelocation.setEditable(false);

		Button btnSelectFileLocation = new Button(generalSettings, SWT.PUSH);
		btnSelectFileLocation.setText(CoreMessages.import_csv_dialog_btn_select_file);
		btnSelectFileLocation.setLayoutData(lblBtn);
		btnSelectFileLocation.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fileDlg = new FileDialog(Display.getDefault().getActiveShell(), SWT.OPEN);
				fileDlg.setText("Open");
				fileDlg.setFilterExtensions(new String[] { "*.csv", "*.*" });
				setFileLocation(fileDlg.open());
				updatePageCompletion();

			}
		});

		Group exporterSettings = new Group(composite, SWT.NONE);
		exporterSettings.setText(CoreMessages.data_transfer_wizard_settings_group_exporter);
		exporterSettings.setLayoutData(new GridData(GridData.FILL_BOTH));
		exporterSettings.setLayout(new GridLayout(1, false));

		propsEditor = new PropertyTreeViewer(exporterSettings, SWT.BORDER);

		setControl(composite);

	}

	private void setFileLocation(String location) {
		CsvDataTransferSettings settings = getWizard().getPageSettings(this, CsvDataTransferSettings.class);
		
		if(location==null) {
			txtFilelocation.setText("");
			settings.setFileLocation("");
			return;
		}
		if (txtFilelocation != null && !txtFilelocation.isDisposed()) {
			txtFilelocation.setText(location);
			settings.setFileLocation(location);
		}
	}

	@Override
	public void activatePage() {

		DataTransferProcessorDescriptor processor = getWizard().getSettings().getProcessor();
		propertySource = new PropertySourceCustom(processor.getProperties(),
				getWizard().getSettings().getProcessorProperties());
		propsEditor.loadProperties(propertySource);

		updatePageCompletion();
	}

	@Override
	protected boolean determinePageCompletion() {
		CsvDataTransferSettings settings = getWizard().getPageSettings(this, CsvDataTransferSettings.class);
		if (!settings.getFileLocation().isEmpty()) {
			return true;
		}
		return false;
	}
}
