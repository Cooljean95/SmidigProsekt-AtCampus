import React, {useRef, useState} from 'react';
import {
	ActivityIndicator,
	Image,
	Keyboard,
	KeyboardAvoidingView,
	StyleSheet, TouchableOpacity,
	TouchableWithoutFeedback,
	View
} from "react-native";
import InputField from "../components/InputField";
import SimpleButton from "../components/SimpleButton";

import * as ImagePicker from 'expo-image-picker';
import {Ionicons} from "@expo/vector-icons";
import {createGroup} from "../services/GroupService";
import Toast from "react-native-toast-message";
import upload from "../services/ImageService";

function Separator() {
	return <View style={styles.separator}/>
}

function CreateGroupScreen({navigation}) {
	const [image, setImage] = useState(null);
	const [groupName, setGroupName] = useState("");
	//const [subject, setSubject] = useState("");
	const [description, setDescription] = useState("");
	const [uploading, setUploading] = useState(false);
	
	const groupNameRef = useRef();
	const subjectRef = useRef();
	const descriptionRef = useRef();
	
	const pickImage = async () => {
		// No permissions request is necessary for launching the image library
		let result = await ImagePicker.launchImageLibraryAsync({
			mediaTypes: ImagePicker.MediaTypeOptions.Images,
			base64: true,
			allowsEditing: true,
			aspect: [1, 1],
			quality: 1,
		});
		
		if (!result.cancelled) {
			setUploading(true);
			setImage(null)

			const url = await upload(result.base64)

			setImage(url)
			setUploading(false)
		}
	};
	
	function makeGroup() {
		createGroup(groupName, description, image, 1, 1)
			.then(response => {
				const group = response.data;
				Toast.show({
					type: 'success',
					text1: 'Successfully created group',
					text2: group.name,
				});
				navigation.goBack()
			})
			.catch(err => {
				console.log("Failed to create group", err);
				
				Toast.show({
					type: 'error',
					text1: 'Failed to create group',
				});
			})
			.finally(() => {
				setImage(null);
				setGroupName("");
				setDescription("");
			})
	}
	
	return (
		<TouchableWithoutFeedback onPress={() => Keyboard.dismiss()}>
			<View style={{flex: 1, backgroundColor: "white"}}>
				<KeyboardAvoidingView contentContainerStyle={{backgroundColor: "white", alignItems: "center"}} behavior={"position"} keyboardVerticalOffset={80}>
					<TouchableOpacity
						style={styles.imageWrapper}
						activeOpacity={0.6}
						onPress={() => pickImage()}
					>
						{image && <Image
							source={{uri: image}}
							style={styles.image}
						/>}
						
						{!image &&
							(uploading ?
								<ActivityIndicator/> :
								<Ionicons name="image-outline" size={55} color="rgba(32, 32, 32, 0.8)" />)
						}
					</TouchableOpacity>
					
					<View style={styles.divider}/>
					
					<View style={styles.inputs}>
						<InputField
							innerRef={groupNameRef}
							title={"Group name"}
							placeholder={"Group name.."}
							onSubmitEditing={() => subjectRef.current?.focus?.()}
							blurOnSubmit={false}
							value={groupName}
							returnKeyType={"next"}
							onChangeText={val => setGroupName(val)}
						/>
						
						{/*<Separator/>
						
						<InputField
							innerRef={subjectRef}
							title={"Subject"}
							placeholder={"Subject.."}
							onSubmitEditing={() => descriptionRef.current?.focus?.()}
							blurOnSubmit={false}
							returnKeyType={"next"}
							value={subject}
							onChangeText={val => setSubject(val)}
						/>*/}
						
						<Separator/>
						
						<InputField
							innerRef={descriptionRef}
							title={"Description"}
							placeholder={"Description.."}
							multiline
							value={description}
							onChangeText={val => setDescription(val)}
							onSubmitEditing={() => createGroup()}
						/>
					</View>
					
					<View style={styles.buttonWrapper}>
						{!uploading && <SimpleButton text={"Create"} onPress={() => makeGroup()}/>}
					</View>
				</KeyboardAvoidingView>
			</View>
		</TouchableWithoutFeedback>
	);
}

const styles = StyleSheet.create({
	container: {
		alignItems: 'center',
		justifyContent: 'center',
		backgroundColor: "white",
	},
	imageWrapper: {
		display: "flex",
		alignItems: "center",
		justifyContent: "center",
		width: 150,
		height: 150,
		borderRadius: 75,
		backgroundColor: "rgb(235, 235, 235)",
		margin: 20
	},
	image: {
		width: 150,
		height: 150,
		borderRadius: 75,
	},
	divider: {
		height: 1,
		backgroundColor: "#f2f4f5",
		width: "100%",
	},
	inputs: {
		width: "100%",
		padding: 20,
	},
	separator: {
		height: 20,
	},
	buttonWrapper: {
		padding: 20,
		width: "100%",
	}
})

export default CreateGroupScreen;
