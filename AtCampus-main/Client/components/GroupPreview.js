import React from 'react';
import {Image, StyleSheet, Text, TouchableOpacity, View} from "react-native";

function GroupPreview({image, name, onPress}) {
	return (
		<TouchableOpacity
			activeOpacity={0.6}
			onPress={onPress}
			style={styles.container}
		>
			<Image
				style={styles.image}
				source={{uri: image}}
			/>
			
			<Text style={styles.name}>{name}</Text>
		</TouchableOpacity>
	);
}

const styles = StyleSheet.create({
	container: {
		display: "flex",
		width: 100,
	},
	image: {
		width: 100,
		height: 100,
		borderRadius: 20,
		marginBottom: 10,
	},
	name: {
		fontSize: 16,
		fontWeight: "bold",
	}
})

export default GroupPreview;
