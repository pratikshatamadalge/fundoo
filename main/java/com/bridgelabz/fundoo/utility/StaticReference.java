package com.bridgelabz.fundoo.utility;

public class StaticReference {

	private StaticReference() {

	}

	public static final String SUCCESSFULL = "successfull";
	public static final String INVALID = "invalid credentials ";
	public static final String UPDATE = "updated";
	public static final String DELETE = "deleted";
	public static final String FETCH = "all data fetched";
	public static final String EXIST = "already exist";
	public static final String NOTEXIST = "data not exist";

//user controller info
	public static final String CONTROLLER_REGISTER_USER = "controller:register user";
	public static final String CONTROLLER_LOGIN_USER = "controller:login user";
	public static final String CONTROLLER_GET_USER = "controller:get user";
	public static final String CONTROLLER_UPDATE_USER = "controller:update user";
	public static final String CONTROLLER_DELETE_USER = "controller:delete user";
	public static final String CONTROLLER_FORGOT = "controller:forgot password";
	public static final String CONTROLLER_RESET = "controller:reset password";
	public static final String CONTROLLER_VALIDATE_USER = "controller:validate user";

//note controller info
	public static final String CONTROLLER_CREATE_NOTE = "Controller: create note";
	public static final String CONTROLLER_GET_NOTE = "Controller: get note";
	public static final String CONTROLLER_UPDATE_NOTE = "controller:update note";
	public static final String CONTROLLER_DELETE_NOTE = "controller:delete note";
	public static final String CONTROLLER_PIN_NOTE = "controller:pin note";
	public static final String CONTROLLER_ARCHIVE_NOTE = "controller:archive note";
	public static final String CONTROLLER_TRASH_NOTE = "controller:trash note";
	public static final String CONTROLLER_ADD_LABEL = "controller:add label";
	public static final String CONTROLLER_SORT_BY_TITLE = "controller:sort by title";
	public static final String CONTROLLER_SORT_BY_DATE = "controller:sort by date";
	public static final String CONTROLLER_ADD_COLLABRATOR = "controller:add collabrator";
	public static final String CONTROLLER_GET_COLLABRATOR="controller:get collabrator";
	public static final String CONTROLLER_ADD_REMAINDER = "controller:add remainder";
	public static final String CONTROLLER_UPDATE_REMAINDER = "controller:update remainder";
	public static final String CONTROLLER_DELETE_REMAINDER = "controller: delete remainder";

//label controller info
	public static final String CONTROLLER_CREATE_LABEL = "controller:create label";
	public static final String CONTROLLER_UPDATE_LABEL = "controller:update label";
	public static final String CONTROLLER_DELETE_LABEL = "controller:delete label";
	public static final String CONTROLLER_GET_LABEL = "controller:get label";
}
