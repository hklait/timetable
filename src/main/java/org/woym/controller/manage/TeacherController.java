package org.woym.controller.manage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.woym.exceptions.DatasetException;
import org.woym.objects.Teacher;
import org.woym.persistence.TeacherDAO;

/**
 * <h1>TeacherController</h1>
 * <p>
 * Dieser Controller ist für die allgemeine Lehrkraftverwaltung zuständig.
 * 
 * @author Tim Hansen (tihansen)
 *
 */
@SessionScoped
@ManagedBean(name = "teacherController")
public class TeacherController implements Serializable {

	private static final long serialVersionUID = -2341971622906815080L;;

	private Teacher selectedTeacher;
	private Teacher selectedTeacherForSearch;
	
	private String searchSymbol;

	public List<Teacher> getTeachers() {
		try {
			return TeacherDAO.getInstance().getAll("symbol");
		} catch (DatasetException e) {
			FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_ERROR,
					"Fehler beim Laden der Lehrer", "");
			FacesContext.getCurrentInstance().addMessage(null, message);
			return new ArrayList<Teacher>();
		}
	}

	public void addTeacherDialog() {

		Map<String, Object> options = new HashMap<String, Object>();
		options.put("modal", true);
		options.put("draggable", false);
		options.put("resizable", false);
		options.put("contentHeight", 400);
		options.put("contentWidth", 600);

		RequestContext rc = RequestContext.getCurrentInstance();
		rc.openDialog("manageTeachersDialog", options, null);
	}

	public void editTeacherDialog() {

		Map<String, Object> options = new HashMap<String, Object>();
		options.put("modal", true);
		options.put("draggable", false);
		options.put("resizable", false);
		options.put("contentHeight", 400);
		options.put("contentWidth", 600);

		RequestContext rc = RequestContext.getCurrentInstance();
		rc.openDialog("editTeacherDialog", options, null);
	}

	public void onTeacherAdded(SelectEvent event) {

		Teacher teacher = (Teacher) event.getObject();

		try {
			TeacherDAO.getInstance().persist(teacher);
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Lehrer hinzugefügt", teacher.getName() + " ("
							+ teacher.getSymbol() + ")");
			FacesContext.getCurrentInstance().addMessage(null, message);
		} catch (DatasetException e) {
			FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Lehrer existiert bereits", "");
			FacesContext.getCurrentInstance().addMessage(null, message);
			return;
		}
	}

	public void deleteTeacher() {
		if (selectedTeacher != null) {
			try {
				TeacherDAO.getInstance().delete(selectedTeacher);
				FacesMessage message = new FacesMessage(
						FacesMessage.SEVERITY_INFO, "Lehrer gelöscht",
						selectedTeacher.getName() + " ("
								+ selectedTeacher.getSymbol() + ")");
				FacesContext.getCurrentInstance().addMessage(null, message);
			} catch (DatasetException e) {
				FacesMessage message = new FacesMessage(
						FacesMessage.SEVERITY_ERROR,
						"Fehler beim Löschen des Lehrers", "");
				FacesContext.getCurrentInstance().addMessage(null, message);
			}
		}
	}

	public ArrayList<Teacher> getTeachersForSearch() {
		
		ArrayList<Teacher> tempList = new ArrayList<>();
		
		if(searchSymbol == null || "".equals(searchSymbol)) {
			for(Teacher teacher : getTeachers()) {
				tempList.add(teacher);
				
				if(tempList.size() >= 5) {
					return tempList;
				}

			}
			
			return tempList;
		}
		
		for(Teacher teacher : getTeachers()) {
			
			if(teacher.getSymbol().contains(searchSymbol)) {
				
				tempList.add(teacher);
				
				if(tempList.size() >= 5) {
					return tempList;
				}
			}
		}
		
		return tempList;
	}
	
	public Teacher getSelectedTeacher() {
		return selectedTeacher;
	}

	public void setSelectedTeacher(Teacher selectedTeacher) {
		this.selectedTeacher = selectedTeacher;
	}
	
	public String getSearchSymbol() {
		return searchSymbol;
	}

	public void setSearchSymbol(String searchSymbol) {
		this.searchSymbol = searchSymbol;
	}

	public Teacher getSelectedTeacherForSearch() {
		return selectedTeacherForSearch;
	}

	public void setSelectedTeacherForSearch(Teacher selectedTeacherForSearch) {
		this.selectedTeacherForSearch = selectedTeacherForSearch;
	}
}
