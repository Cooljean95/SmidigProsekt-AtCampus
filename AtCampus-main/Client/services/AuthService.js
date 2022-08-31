import AxiosService from "./AxiosService";
import * as SecureStore from 'expo-secure-store';

const instance = AxiosService.getInstance();

const register = (firstName, lastName, email, phoneNumber, school, program, password, image) => {
	const request = {
		firstName,
		lastName,
		email,
		phoneNumber,
		password,
		school,
		program,
		userProfileImage: image,
	}
	
	console.log("Register request", request);
	
	return instance.post("register", request);
}

const refresh = (refreshToken) => {
	return instance.post("refresh", refreshToken);
}

const login = (email, password) => {
	return instance.post("login", {
		email,
		password,
	})
}

const check = async () => {
	const token = await SecureStore.getItemAsync("token");
	const username = await SecureStore.getItemAsync("username");
	const userId = await SecureStore.getItemAsync("userId");
	
	if (token && username && userId) {
		return {token, username, userId}
	}
	
	return null
}

const logout = async () =>{
	 await SecureStore.deleteItemAsync("token")
	 await SecureStore.deleteItemAsync("refresh_token")
}

export default {
	register,
	login,
	refresh,
	check,
	logout
}
