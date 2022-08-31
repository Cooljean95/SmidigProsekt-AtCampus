import React from 'react';
import {Image, StyleSheet, Text, TouchableOpacity, View} from "react-native";
import {AntDesign} from "@expo/vector-icons";

function CreateGroup({onPress}) {
	return (
		<TouchableOpacity
			activeOpacity={0.4}
			onPress={onPress}
			style={styles.container}
		>
			<AntDesign name="plus" size={28} color="black" />
		</TouchableOpacity>
	);
}

const styles = StyleSheet.create({
	container: {
		display: "flex",
		alignItems: "center",
		justifyContent: "center",
		width: 100,
		height: 100,
		borderRadius: 20,
		backgroundColor: "#f6f6f6",
		marginLeft: 10,
	},
})

export default CreateGroup;
