import React from 'react';
import {StyleSheet, Text, View} from "react-native";

export default function CreateAccountTitle() {
	return (
		<View style={styles.container}>
			<Text style={styles.title}>Create your account</Text>
		</View>
	);
}

const styles = StyleSheet.create({
	container: {
		flexDirection: "row",
		alignItems: "center",
		marginBottom: 30,
		marginTop: 50,
	},
	title: {
		fontSize: 25,
		fontWeight: "bold"
	},
});
