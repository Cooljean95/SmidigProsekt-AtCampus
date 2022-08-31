import React, {useEffect, useState} from 'react';
import {FlatList, StyleSheet, Text, TextInput, TouchableOpacity, View} from "react-native";
import WideGroupPreview from "../components/WideGroupPreview";
import {AntDesign} from "@expo/vector-icons";
import {getAllGroups, requestToJoinGroup} from "../services/GroupService";
import {useSelector} from "react-redux";
import Toast from "react-native-toast-message";

function Separator() {
	return <View style={styles.separator}/>
}

function FindGroupScreen({navigation}) {
	const [search, setSearch] = useState("");
	const [groups, setGroups] = useState([])
	const [searchedGroups, setSearchedGroups] = useState([])

	const {userId} = useSelector(state => state.auth)

	useEffect(() => {
		getAllGroups()
			.then((res) => {
				setGroups(res.data)
				setSearchedGroups(res.data)
			})
			.catch((err) => {
				console.log("Error in FindGroupScreen " + err)
			})
	}, [])


	function onChangeHandler(text){
		let matches = []

		if (text.length > 0) {
			matches = groups.filter( group => {
				const regex = new RegExp(`${text}`, "gi")
				return group.name.match(regex);
			})
		} else if (text.length === 0){
			setSearchedGroups(groups)
			setSearch(text)
			return
		}

		setSearchedGroups(matches)
		setSearch(text)

	}


	return (

		<View style={styles.container}>
			<FlatList
				contentContainerStyle={styles.postPreviews}
				data={searchedGroups}
				ListHeaderComponent={
					<View style={styles.header}>
						<View style={styles.searchBarWrapper}>
							<AntDesign
								style={styles.searchIcon}
								name={"search1"}
								size={18}
								color={"black"}
							/>

							<TextInput
								style={styles.searchBar}
								placeholder={"Search for a group.."}
								value={search}
								onChangeText={text => onChangeHandler(text)}
							/>
						</View>

						<TouchableOpacity style={styles.filterButton}>
							<Text>Filter</Text>
						</TouchableOpacity>
					</View>
				}
				ItemSeparatorComponent={Separator}
				renderItem={({item}) =>
					<WideGroupPreview
						name={item.name}
						description={item.description}
						members={item.members}
						image={item.image}
						onPress={() => {
							//onGroupPress(userId, item.id)
							navigation.navigate("Send Group Request", {
								group: item,
							})
						}}
					/>
				}
			/>
		</View>
	);
}

function onGroupPress(userId, groupId){
	// requestToJoinGroup(userId, groupId)
	// 	.then((res) => {
	// 		Toast.show({
	// 			type: 'success',
	// 			text1: 'Sent request to join group',
	// 		});
	// 	})
	// 	.catch((err) => {
	// 		Toast.show({
	// 			type: 'error',
	// 			text1: 'Could not send request to join group',
	// 		});
	//
	// 		console.log("Error in onGroupPress: " + err)
	// 	})
}

const styles = StyleSheet.create({
	container: {
		flex: 1,
		backgroundColor: "white",
	},
	postPreviews: {
		padding: 20,
	},
	separator: {
		height: 15,
	},
	header: {
		display: "flex",
		alignItems: "flex-start",
		marginBottom: 20,
	},
	searchBarWrapper: {
		display: "flex",
		flexDirection: "row",
		alignItems: "center",
		marginBottom: 10,
		backgroundColor: "rgb(240, 240, 240)",
		borderRadius: 15,
	},
	searchIcon: {
		padding: 15,
	},
	searchBar: {
		flex: 1,
		height: "100%",
		//borderWidth: 1,
		//borderColor: "rgb(230, 230, 230)",
		fontSize: 16,
		borderRadius: 10,
	},
	filterButton: {
		display: "flex",
		alignItems: "center",
		backgroundColor: "rgb(240, 240, 240)",
		paddingVertical: 10,
		paddingHorizontal: 15,
		borderRadius: 20,
	}
})

export default FindGroupScreen;
