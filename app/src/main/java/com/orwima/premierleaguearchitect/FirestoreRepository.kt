package com.orwima.premierleaguearchitect

import com.google.firebase.firestore.FirebaseFirestore

class FirestoreRepository {
    private val db = FirebaseFirestore.getInstance()

    fun fetchPlayers(
        teamName: String,
        onSuccess: (List<Player>) -> Unit,
        onError: (Exception) -> Unit
    ) {
        db.collection("players")
            .whereEqualTo("teamName", teamName)
            .get()
            .addOnSuccessListener { result ->

                val players = result.documents
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
                onSuccess(players)
            }
            .addOnFailureListener { e -> onError(e) }
    }

    fun saveLineup(
        lineupName: String,
        lineup: Lineup,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        db.collection("lineups")
            .document(lineupName)
            .set(lineup)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onError(e) }
    }

    fun fetchLineup(
        lineupName: String,
        onSuccess: (Lineup?) -> Unit,
        onError: (Exception) -> Unit
    ) {
        db.collection("lineups")
            .document(lineupName)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val lineup = Lineup(
                        team = document.getString("team") ?: "",
                        formation = document.getString("formation") ?: "",
                        positions = document.get("positions") as? Map<String, String> ?: emptyMap()
                    )
                    onSuccess(lineup)
                } else {
                    onSuccess(null)
                }
            }
            .addOnFailureListener { e -> onError(e) }
    }
}