import {
	LOGIN_FAIL,
	LOGIN_SUCCESS,
	LOGOUT,
	REGISTER_FAIL,
	REGISTER_SUCCESS,
	SET_MESSAGE,
} from "./type";

import AuthService from "../services/AuthService";
import * as SecureStore from 'expo-secure-store';
import Toast from "react-native-toast-message";
import {getUserIdByEmail} from "../services/UserService";

export const register = (firstName, lastName, email, phoneNumber, password, school, program, image) => (dispatch) => {
	return AuthService.register(firstName, lastName, email, phoneNumber, password, school, program, image)
		.then(response => {
			dispatch({
				type: REGISTER_SUCCESS,
			});

			dispatch({
				type: SET_MESSAGE,
				payload: 2,
			})

			Toast.show({
				type: "success",
				text1: "Successfully created account",
				text2: "Please log in"
			})
			
			console.log("Response from register", response);
		})
		.catch(error => {
			const message = (error.response &&
					error.response.data &&
					error.response.data.message) ||
				error.message ||
				error.toString();
			
			console.log("Error", message)

			Toast.show({
				type: "error",
				text1: "Failed to create account",
				text2: "Please try again"
			})

			dispatch({
				type: REGISTER_FAIL,
			});
			
			dispatch({
				type: SET_MESSAGE,
				payload: message,
			});
		});
}
export const check = () => dispatch => {
	return AuthService.check()
		.then(response => {
			if (response) {
				const {token, username, userId} = response;
				
				dispatch({
					type: LOGIN_SUCCESS,
					payload: {username, userId},
				})
			}
		})
}

export const login = (username, password) => dispatch => {
	return AuthService.login(username, password)
		.then(response => {
			const tokens = response.data;
			const {token, refreshToken} = tokens;

			SecureStore.setItemAsync("token", token);
			SecureStore.setItemAsync("refresh_token", refreshToken);
			SecureStore.setItemAsync("username", username);
			
			getUserIdByEmail(username).then((userId) => {
				console.log("Set item userId", userId);
				
				SecureStore.setItemAsync("userId", String(userId))
				
				dispatch({
					type: LOGIN_SUCCESS,
					payload: {username, userId},
				})
			})
		})
		.catch(error => {
			const message = (error.response &&
					error.response.data &&
					error.response.data.message) ||
				error.message ||
				error.toString();
			
			dispatch({
				type: LOGIN_FAIL,
			});
			
			dispatch({
				type: SET_MESSAGE,
				payload: message,
			});
		})
}




export const logout = () => dispatch => {
	AuthService.logout()
		.then(() => {
			dispatch({
				type: LOGOUT,
			})
		})
		.catch(error => {
			console.log(error)
		})
	;

}
