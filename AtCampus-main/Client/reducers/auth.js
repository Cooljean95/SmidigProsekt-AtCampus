import {LOGIN_FAIL, LOGIN_SUCCESS, LOGOUT, REGISTER_FAIL, REGISTER_SUCCESS,} from "../actions/type";

// const user = JSON.parse(localStorage.getItem("user"));
// const initialState = user
// 	? {isLoggedIn: true, user}
// 	: {isLoggedIn: false, user: null};

const initialState = {isLoggedIn: false, user: null};

export default function (state = initialState, action) {
	const {type, payload} = action;
	switch (type) {
		case REGISTER_SUCCESS:
			return {
				...state,
				isLoggedIn: false,
			};
		case REGISTER_FAIL:
			return {
				...state,
				isLoggedIn: false,
			};
		case LOGIN_SUCCESS:
			return {
				...state,
				isLoggedIn: true,
				username: payload.username,
				userId: payload.userId,
			};
		case LOGIN_FAIL:
			return {
				...state,
				isLoggedIn: false,
				username: null,
				userId: null,
			};
		case LOGOUT:
			return {
				...state,
				isLoggedIn: false,
				username: null,
				userId: null,
			};
		default:
			return state;
	}
}
