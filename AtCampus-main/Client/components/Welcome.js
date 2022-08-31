import React from 'react';
import {StyleSheet, Text, View} from "react-native";
import { Feather } from '@expo/vector-icons';

function Welcome({title, date, week, temperature}) {
	return (
		<View style={styles.container}>
			<View style={styles.infoContainer}>
				<Text style={styles.title}>{title}</Text>
				<Text style={styles.date}>{date}</Text>
				<Text style={styles.week}>{week}</Text>
			</View>
			
			<View style={styles.weatherContainer}>
				<Feather
					style={styles.sun}
					name={"sun"}
					size={24}
					color={"#FFD66B"}
				/>
				<Text>{temperature}°C</Text>
			</View>
		</View>
	);
}

const styles = StyleSheet.create({
	container: {
		display: "flex",
		flexDirection: "row",
		alignItems: "center",
	},
	infoContainer: {
	
	},
	title: {
		fontSize: 28,
		fontWeight: "bold",
		marginBottom: 2,
	},
	date: {
		marginBottom: 2,
	},
	week: {
	
	},
	sun: {
		marginBottom: 5,
	},
	weatherContainer: {
		display: "flex",
		alignItems: "center",
		marginLeft: "auto",
	}
})

export default Welcome;
