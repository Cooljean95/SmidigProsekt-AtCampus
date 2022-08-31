import {ScrollView, StyleSheet, View} from "react-native";
import {StatusBar} from "expo-status-bar";
import LoginInput from "../../components/LoginInput";
import LoginButton from "../../components/LoginButton";
import {CreateUserContext} from "../../global/CreateUserContext";
import {useContext} from "react";
import CreateAccountTitle from "../../components/CreateAccountTitle";
import Toast from "react-native-toast-message";


export default function MakeUserName({navigation,}) {
	const {
		setFirstname,
		setLastname,
		firstname,
		lastname
	} = useContext(CreateUserContext)
	
	function onPress() {
		if (firstname !== "" && lastname !== "") {
			navigation.navigate("makeUserInfo")
		} else {
			Toast.show({
				type: "error",
				text1: "Invalid name input",
				text2: "Firstname and Lastname cant be empty",
			})
		}
	}
	
	return (
		<View style={styles.container}>
			<ScrollView style={styles.scroll}>
				<CreateAccountTitle/>
				
				<LoginInput
					title={"First name"}
					value={firstname}
					onChangeText={val => setFirstname(val)}
					keyboardType={"default"}
				/>
				
				<LoginInput
					title={"Last name"}
					value={lastname}
					onChangeText={val => setLastname(val)}
					keyboardType={"default"}
				/>
				
				<StatusBar style="auto"/>
				
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
