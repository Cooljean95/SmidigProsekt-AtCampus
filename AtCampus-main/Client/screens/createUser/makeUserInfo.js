import {ScrollView, StyleSheet, View} from "react-native";
import {StatusBar} from "expo-status-bar";
import LoginInput from "../../components/LoginInput";
import LoginButton from "../../components/LoginButton";
import {useContext} from "react";
import {CreateUserContext} from "../../global/CreateUserContext";
import CreateAccountTitle from "../../components/CreateAccountTitle";
import Toast from "react-native-toast-message";

export default function MakeUserInfo({navigation, getEmail, getPhoneNr}) {
	const {
		setEmail,
		setPhoneNr,
		email,
		phoneNr
	} = useContext(CreateUserContext)
	
	function onPress() {
		let reg = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w\w+)+$/;
		if (email !== "" && phoneNr !== "") {
			if(reg.test(email) === false){
				Toast.show({
					type: "error",
					text1: "Email is not correct",
				})
			}else{
				navigation.navigate("makeUserSchool")
			}
		} else {
			Toast.show({
				type: "error",
				text1:"Invalid Email and phone number input.",
				text2:"Email and phone number can not be empty.",
			})
		}
	}
	
	return (
		<View style={styles.container}>
			<StatusBar style="auto"/>
			<ScrollView style={styles.scroll}>
				<CreateAccountTitle/>
				<LoginInput
					title={"Email"}
					value={email}
					onChangeText={(val => setEmail(val))}
					keyboardType={"email-address"}
					autoCapitalize="none"
				/>
				
				<LoginInput
					title={"Phone number"}
					value={phoneNr}
					onChangeText={(val) => setPhoneNr(val)}
					keyboardType={"phone-pad"}
				/>
				
				<LoginButton navigation={navigation} title={"Next"} onPress={onPress}/>
			</ScrollView>
		</View>
	);
}

const styles = StyleSheet.create({
	container: {
		flex: 1,
		backgroundColor: "#fff",
		justifyContent: "center",
	},
	scroll: {
		padding: 20,
	}
});
