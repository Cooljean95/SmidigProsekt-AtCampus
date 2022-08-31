import React, {useState} from 'react';
import {
	Keyboard,
	KeyboardAvoidingView, Platform, ScrollView,
	StyleSheet,
	Text,
	TextInput,
	TouchableWithoutFeedback,
	View
} from "react-native";
import PostPreview from "../components/PostPreview";
import SimpleButton from "../components/SimpleButton";
import {useSelector} from "react-redux";
import {requestToJoinGroup} from "../services/GroupService";
import Toast from "react-native-toast-message";

function SendGroupRequest({navigation, route}) {
	const [message, setMessage] = useState("");
	const {group} = route.params;

	const {userId} = useSelector(state => state.auth)

	function onGroupPress() {
		requestToJoinGroup(userId, group.id, message)
			.then((res) => {
				Toast.show({
					type: 'success',
					text1: 'Sent request to join group',
				});

				navigation.goBack();
			})
			.catch((err) => {
				Toast.show({
					type: 'error',
					text1: 'Could not send request to join group',
				});

				console.log("Error in onGroupPress: " + err)
			})
	}

	return (
		<TouchableWithoutFeedback onPress={() => Keyboard.dismiss()}>
			<KeyboardAvoidingView
				style={styles.wrapper}
				behavior= {(Platform.OS === 'ios') ? "padding" : null}
				keyboardVerticalOffset={Platform.select({ios: 80, android: 0})}
			>
				<View style={styles.container}>
					<PostPreview
						title={group.name}
						preview={group.description}
						image={group.image}
					/>

					<Text style={styles.title}>
						Send request to join a group
					</Text>

					<Text style={styles.description}>
						Becoming part of a study group is an anonymous process. Write a little about yourself and send a request to join
					</Text>

					<Text style={styles.message}>Message</Text>

					<TextInput
						multiline
						style={styles.input}
						value={message}
						maxHeight={120}
						onChangeText={text => setMessage(text)}
					/>

					<View style={{marginTop: "auto"}}>
						<SimpleButton
							text={"Send request"}
							style={styles.button}
							onPress={() => {
								onGroupPress();
							}}
						/>
					</View>
				</View>
			</KeyboardAvoidingView>
		</TouchableWithoutFeedback>
	);
}

const styles = StyleSheet.create({
	wrapper: {
		flex: 1,
	},
	container: {
		display: "flex",
		flex: 1,
		backgroundColor: "white",
		padding: 20,
	},
	title: {
		fontSize: 22,
		fontWeight: "bold",
		marginBottom: 10,
		marginTop: 20
	},
	description: {
		fontSize: 16,
	},
	message: {
		fontWeight: "bold",
		fontSize: 20,
		marginTop: 20,
		marginBottom: 10,
	},
	input: {
		borderWidth: 1,
		borderColor: "rgb(240, 240, 240)",
		borderRadius: 10,
		fontSize: 16,
		padding: 10,
		paddingTop: 10,
	},
	button: {
		marginTop: "auto",
	}
})

export default SendGroupRequest;
