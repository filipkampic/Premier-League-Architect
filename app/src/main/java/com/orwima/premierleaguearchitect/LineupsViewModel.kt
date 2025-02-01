package com.orwima.premierleaguearchitect

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.flow.MutableStateFlow

class LineupsViewModel: ViewModel() {
    private val db = FirebaseFirestore.getInstance()

    val lineups = MutableStateFlow<List<Pair<String, Lineup>>>(emptyList())
    val players = MutableStateFlow<List<Player>>(emptyList())
    val selectedLineup = MutableStateFlow<Lineup?>(null)

    fun fetchPlayers(teamName: String) {
        db.collection("players")
            .whereEqualTo("teamName", teamName)
            .get()
            .addOnSuccessListener { result ->
                val fetchedPlayers = result.documents
                    .flatMap { document ->
                        val playersArray = document.get("players") as? List<Map<String, Any>> ?: emptyList()

                        playersArray.map { playerData ->
                            Player(
                                name = playerData["name"] as? String ?: "",
                                image = playerData["image"] as? String ?: "",
                                team = teamName,
                                positions = playerData["positions"] as? List<String> ?: emptyList()
                            )
                        }
                    }
                players.value = fetchedPlayers
            }
    }

    fun saveLineup(
        oldLineupName: String?,
        newLineupName: String,
        lineup: Lineup
    ) {
        val lineupRef = db.collection("lineups")
        val lineupWithTimeStamp = lineup.copy(timestamp = System.currentTimeMillis())

        if (oldLineupName != null && oldLineupName == newLineupName) {
            lineupRef.document(newLineupName)
                .set(lineupWithTimeStamp)
                .addOnSuccessListener {
                    fetchTeamLineups(lineup.team)
                }
        } else {
            if (!oldLineupName.isNullOrEmpty()) {
                lineupRef.document(oldLineupName)
                    .delete()
                    .addOnSuccessListener {
                        lineupRef.document(newLineupName)
                            .set(lineupWithTimeStamp)
                            .addOnSuccessListener {
                                fetchTeamLineups(lineup.team)
                            }
                    }
            } else {
                lineupRef.document(newLineupName)
                    .set(lineupWithTimeStamp)
                    .addOnSuccessListener {
                        fetchTeamLineups(lineup.team)
                    }
            }
        }
    }

    fun fetchAllLineups() {
        db.collection("lineups")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { result ->
                val fetchedLineups = result.documents.mapNotNull { document ->
                    val lineupName = document.id
                    val lineupData = document.toObject(Lineup::class.java)
                    if (lineupData != null) lineupName to lineupData else null
                }
                lineups.value = fetchedLineups
            }
    }

    fun fetchTeamLineups(teamName: String) {
        db.collection("lineups")
            .whereEqualTo("team", teamName)
            .get()
            .addOnSuccessListener { result ->
                val fetchedLineups = result.documents.mapNotNull { document ->
                    val lineupName = document.id
                    val lineupData = document.toObject(Lineup::class.java)
                    if (lineupData != null) lineupName to lineupData else null
                }
                lineups.value = fetchedLineups
            }
    }

    fun fetchLineup(lineupName: String) {
        db.collection("lineups").document(lineupName)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val lineup = document.toObject(Lineup::class.java)
                    selectedLineup.value = lineup
                } else {
                    selectedLineup.value = null
                }
            }
    }

    fun deleteLineup(lineupName: String, teamName: String) {
        db.collection("lineups").document(lineupName)
            .delete()
            .addOnSuccessListener {
                fetchTeamLineups(teamName)
            }
    }
}
