/*
 * DBeaver - Universal Database Manager
 * Copyright (C) 2010-2017 Serge Rider (serge@jkiss.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jkiss.dbeaver.tools.transfer.wizard;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.jkiss.dbeaver.Log;
import org.jkiss.dbeaver.core.CoreMessages;
import org.jkiss.dbeaver.model.DBPEvaluationContext;
import org.jkiss.dbeaver.model.DBUtils;
import org.jkiss.dbeaver.registry.transfer.DataTransferNodeDescriptor;
import org.jkiss.dbeaver.tools.transfer.IDataTransferNode;
import org.jkiss.dbeaver.tools.transfer.IDataTransferProcessor;
import org.jkiss.dbeaver.tools.transfer.IDataTransferSettings;
import org.jkiss.dbeaver.tools.transfer.handlers.DataTransferStrategy;
import org.jkiss.dbeaver.tools.transfer.stream.csv.CsvDataTransferSettings;
import org.jkiss.dbeaver.ui.DBeaverIcons;
import org.jkiss.dbeaver.ui.UIUtils;
import org.jkiss.dbeaver.ui.dialogs.ActiveWizardPage;

class DataTransferPageFinal extends ActiveWizardPage<DataTransferWizard> {

    private static final Log log = Log.getLog(DataTransferPageFinal.class);

    private Table resultTable;
    private boolean activated = false;
 
    DataTransferPageFinal() {
        super(CoreMessages.data_transfer_wizard_final_name);
        setTitle(CoreMessages.data_transfer_wizard_final_title);
        setDescription(CoreMessages.data_transfer_wizard_final_description);
        setPageComplete(false);
    }
    

    @Override
    public void createControl(Composite parent) {
        initializeDialogUnits(parent);

        Composite composite = new Composite(parent, SWT.NULL);
        GridLayout gl = new GridLayout();
        gl.marginHeight = 0;
        gl.marginWidth = 0;
        composite.setLayout(gl);
        composite.setLayoutData(new GridData(GridData.FILL_BOTH));

        {
            Group tablesGroup = UIUtils.createControlGroup(composite, CoreMessages.data_transfer_wizard_final_group_tables, 3, GridData.FILL_BOTH, 0);

            resultTable = new Table(tablesGroup, SWT.BORDER | SWT.SINGLE | SWT.FULL_SELECTION);
            resultTable.setLayoutData(new GridData(GridData.FILL_BOTH));
            resultTable.setHeaderVisible(true);
            resultTable.setLinesVisible(true);

            UIUtils.createTableColumn(resultTable, SWT.LEFT, CoreMessages.data_transfer_wizard_final_column_source);
            UIUtils.createTableColumn(resultTable, SWT.LEFT, CoreMessages.data_transfer_wizard_final_column_target);

            UIUtils.packColumns(resultTable);
        }

        setControl(composite);
    }

    @Override
    public void activatePage()
    {
        resultTable.removeAll();
        DataTransferSettings settings = getWizard().getSettings();
        List<DataTransferPipe> dataPipes = settings.getDataPipes();
        for (DataTransferPipe pipe : dataPipes) {
        		
        		IDataTransferNode typeObject = null;
        		if(settings.getTransferStrategy() == DataTransferStrategy.EXPORT) {
        			typeObject = pipe.getConsumer();
        		}
        		else if (settings.getTransferStrategy() == DataTransferStrategy.IMPORT) {
        			typeObject = pipe.getProducer();
        		}
            IDataTransferSettings implSettings = settings.getNodeSettings(typeObject);
            IDataTransferProcessor processor = null;
            if (settings.getProcessor() != null) {
                // Processor is optional
                try {
                    processor = settings.getProcessor().getInstance();
                } catch (Throwable e) {
                    log.error("Can't create processor", e);
                    continue;
                }
            }
          
			DataTransferNodeDescriptor producer = settings.getProducer();
			
			DataTransferNodeDescriptor consumerDescr = settings.getConsumer();
			if (settings.getTransferStrategy() == DataTransferStrategy.EXPORT) {
			
				pipe.getConsumer().initTransfer(pipe.getProducer().getSourceObject(), implSettings, processor,
						processor == null ? null : settings.getProcessorProperties());
				
				TableItem item = new TableItem(resultTable, SWT.NONE);
				item.setText(0,
						DBUtils.getObjectFullName(pipe.getProducer().getSourceObject(), DBPEvaluationContext.UI));
				if (producer != null && producer.getIcon() != null) {
					item.setImage(0, DBeaverIcons.getImage(producer.getIcon()));
				}
				item.setText(1, pipe.getConsumer().getTargetName());
				if (settings.getProcessor() != null && settings.getProcessor().getIcon() != null) {
					item.setImage(1, DBeaverIcons.getImage(settings.getProcessor().getIcon()));
				} else if (consumerDescr != null && consumerDescr.getIcon() != null) {
					item.setImage(1, DBeaverIcons.getImage(consumerDescr.getIcon()));
				}
			}
			else if (settings.getTransferStrategy() == DataTransferStrategy.IMPORT) {
				
				
				TableItem item = new TableItem(resultTable, SWT.NONE);
				if(pipe.getConsumer()!=null) {
					item.setImage(1, DBeaverIcons.getImage(consumerDescr.getIcon()));
					String msg = String.format("%s %s",consumerDescr.getName(), pipe.getConsumer().getTargetName());
					item.setText(1,msg);
				}
		        if (producer != null && producer.getIcon() != null) {
	                if(implSettings instanceof CsvDataTransferSettings) {
	                  item.setText(0, ((CsvDataTransferSettings)implSettings).getFile().getName());
	                }
	            }
	            
	            if (settings.getProcessor() != null && settings.getProcessor().getIcon() != null) {
	                item.setImage(0, DBeaverIcons.getImage(settings.getProcessor().getIcon()));
	            } 
			}
        }
        activated = true;
        UIUtils.packColumns(resultTable, true);
        updatePageCompletion();
    }

    public boolean isActivated()
    {
        return activated;
    }

    @Override
    protected boolean determinePageCompletion()
    {
        return activated;
    }

}