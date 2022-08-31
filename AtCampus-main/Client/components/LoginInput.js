import React from 'react';
import {StyleSheet, Text, TextInput, View} from "react-native";

export default function LoginInput({title, secureTextEntry, value, onChangeText, keyboardType}) {
	return (
		<View style={styles.container}>
			<Text style={styles.info}>{title}</Text>
			
			<TextInput
				style={styles.input}
				onChangeText={onChangeText}
				placeholder={title}
				keyboardType={keyboardType}
				value={value}
				secureTextEntry={secureTextEntry}
			/>
		</View>
	);
}

const styles = StyleSheet.create({
	container: {
		display: "flex",
		borderRadius: 20,
		marginBottom: 20,
	},
	info: {
		marginBottom: 10,
		fontSize: 18,
		fontWeight: "bold",
	},
	input: {
		borderWidth: 1,
		borderRadius: 10,
		borderColor: "#d3d3d3",
		paddingVertical: 15,
		width: "100%",
		paddingHorizontal: 10,
	},
});
