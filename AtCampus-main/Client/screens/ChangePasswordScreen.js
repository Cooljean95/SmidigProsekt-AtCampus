import React, {useEffect, useRef, useState} from 'react';
import {
	Keyboard,
	KeyboardAvoidingView,
	StyleSheet,
	Text,
	TextInput,
	TouchableWithoutFeedback,
	View
} from "react-native";
import InputField from "../components/InputField";
import SimpleButton from "../components/SimpleButton";

function Separator() {
	return <View style={styles.separator}/>
}

function ChangePasswordScreen(props) {
	const [oldPassword, setOldPassword] = useState("");
	const [newPassword, setNewPassword] = useState("");
	const [confirmPassword, setConfirmPassword] = useState("");
	const [valid, setValid] = useState(false);
	const [message, setMessage] = useState(null);
	
	const newPasswordRef = useRef();
	const confirmPasswordRef = useRef();
	
	function isValidPassword() {
		return newPassword === confirmPassword;
	}
	
	useEffect(() => {
		if (newPassword.length === 0) return;
		
		if (!valid && isValidPassword()) {
			setValid(true);
		} else if (valid && !isValidPassword()) {
			setValid(false);
			setMessage("Please make sure the passwords match");
		}
	}, [newPassword, confirmPassword]);
	
	return (
		<TouchableWithoutFeedback
			style={{
				flex: 1,
				backgroundColor: "white",
			}}
			onPress={() => Keyboard.dismiss()}
		>
			<View style={styles.container}>
				<KeyboardAvoidingView style={{backgroundColor: "white", flex: 1}} behavior={"position"} keyboardVerticalOffset={30}>
					<Text style={styles.title}>Change password</Text>
					
					<InputField
						title={"Old Password"}
						placeholder={"Old password..."}
						onSubmitEditing={() => newPasswordRef.current?.focus?.()}
						blurOnSubmit={false}
						value={oldPassword}
						returnKeyType={"next"}
						secureTextEntry
						textContentType={"password"}
						autoComplete={"password"}
						onChangeText={val => setOldPassword(val)}
					/>
					
					<Separator/>
					
					<InputField
						title={"New Password"}
						placeholder={"New password.."}
						innerRef={newPasswordRef}
						onSubmitEditing={() => confirmPasswordRef.current?.focus?.()}
						blurOnSubmit={false}
						value={newPassword}
						returnKeyType={"next"}
						secureTextEntry
						textContentType={"newPassword"}
						autoComplete={"password-new"}
						onChangeText={val => setNewPassword(val)}
					/>
					
					<Separator/>
					
					<InputField
						title={"Confirm Password"}
						placeholder={"Confirm password.."}
						innerRef={confirmPasswordRef}
						value={confirmPassword}
						returnKeyType={"done"}
						secureTextEntry
						textContentType={"newPassword"}
						autoComplete={"password-new"}
						onChangeText={val => setConfirmPassword(val)}
					/>
					
					<Separator/>
					
					{message &&
						<Text style={styles.error}>{message}</Text>
					}
					
					{valid &&
						<SimpleButton
							text={"Change Password"}
						/>
					}
				</KeyboardAvoidingView>
			</View>
		</TouchableWithoutFeedback>
	);
}

const styles = StyleSheet.create({
	container: {
		flex: 1,
		backgroundColor: "white",
		padding: 20,
	},
	separator: {
		height: 25,
	},
	title: {
		fontSize: 32,
		fontWeight: "bold",
		marginTop: 100,
		marginBottom: 40,
	},
	error: {
		fontWeight: "500",
		fontSize: 14,
		color: "rgb(255, 125, 100)",
	}
})

export default ChangePasswordScreen;
