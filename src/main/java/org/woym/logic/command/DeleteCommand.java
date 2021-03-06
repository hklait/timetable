package org.woym.logic.command;

import javax.faces.application.FacesMessage;

import org.woym.exceptions.DatasetException;
import org.woym.logic.FailureStatus;
import org.woym.logic.SuccessStatus;
import org.woym.messages.SpecificStatusMessage;
import org.woym.objects.Entity;
import org.woym.spec.logic.ICommand;
import org.woym.spec.logic.IStatus;

/**
 * 
 * @author JurSch
 *
 */
public class DeleteCommand<E extends Entity> implements ICommand {

	private E entity;

	public DeleteCommand(E entity) {
		if (entity == null) {
			throw new IllegalArgumentException("Entity was null");
		}
		this.entity = entity;
	}

	@Override
	public IStatus execute() {
		IStatus status;

		try {
			entity.delete();
			status = new SuccessStatus();
		} catch (DatasetException e) {
			status = new FailureStatus(
					SpecificStatusMessage.DELETE_OBJECT_DATASET_EXCEPTION,
					entity.getClass(), FacesMessage.SEVERITY_ERROR);
		}
		return status;
	}

	@Override
	public IStatus undo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IStatus redo() {
		// TODO Auto-generated method stub
		return null;
	}

}
