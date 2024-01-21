package com.reflexian.levitycosmetics.data.objects.user;

import com.reflexian.levitycosmetics.data.objects.cosmetics.CosmeticType;
import com.reflexian.levitycosmetics.data.objects.cosmetics.hat.AssignedHat;
import com.reflexian.levitycosmetics.data.objects.cosmetics.helpers.Cosmetic;
import com.reflexian.levitycosmetics.data.objects.cosmetics.nickname.AssignedNickname;
import com.reflexian.levitycosmetics.data.objects.cosmetics.titles.AssignedTitle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Builder@Getter@Setter@AllArgsConstructor
public class UserCosmetic {

    // uuid, localCosmeticId, playerCosmeticId, cosmeticType, selected
    private UUID uuid;
    private String localCosmeticId; // for titles and assigned nicknames, same as playerCosmeticId
    private String playerCosmeticId;
    private CosmeticType cosmeticType;
    private boolean selected;


    public static UserCosmetic fromCosmetic(UUID uuid, Cosmetic cosmetic) {
        if (cosmetic == null) {
            throw new RuntimeException("Cosmetic is null. Uh oh!");
        }

        if (cosmetic instanceof AssignedNickname || cosmetic instanceof AssignedTitle) {
            String id = (cosmetic instanceof AssignedNickname) ? ((AssignedNickname) cosmetic).getCosmeticId() : ((AssignedTitle) cosmetic).getCosmeticId();
            return UserCosmetic.builder()
                    .uuid(uuid)
                    .localCosmeticId(id)
                    .playerCosmeticId(id)
                    .cosmeticType(cosmetic.getType())
                    .selected(false)
                    .build();
        } else if (cosmetic instanceof AssignedHat) {
            AssignedHat assignedHat = (AssignedHat) cosmetic;
            return UserCosmetic.builder()
                    .uuid(uuid)
                    .localCosmeticId(assignedHat.getCosmeticId())
                    .playerCosmeticId(assignedHat.getCosmeticId())
                    .cosmeticType(cosmetic.getType())
                    .selected(false)
                    .build();
        }

        return UserCosmetic.builder()
                .uuid(uuid)
                .localCosmeticId(cosmetic.getName())
                .playerCosmeticId(UUID.randomUUID().toString()+uuid.toString().substring(0,8))
                .cosmeticType(cosmetic.getType())
                .selected(false)
                .build();
    }


    private transient Cosmetic cachedCosmetic = null;
    public Cosmetic getCosmetic() {

        if (cosmeticType == CosmeticType.ASSIGNED_NICKNAME) {
            UserData userData = UserDataService.shared.retrieveUserFromCache(uuid);
            if (userData == null) return null;
            return userData.getAssignedNicknames().stream().filter(nickname -> nickname.getCosmeticId().equals(playerCosmeticId)).findFirst().orElse(null);
        } else if (cosmeticType == CosmeticType.ASSIGNED_TITLE) {
            UserData userData = UserDataService.shared.retrieveUserFromCache(uuid);
            if (userData == null) return null;
            return userData.getAssignedTitles().stream().filter(title -> title.getCosmeticId().equals(playerCosmeticId)).findFirst().orElse(null);
        } else if (cosmeticType == CosmeticType.ASSIGNED_HAT) {
            UserData userData = UserDataService.shared.retrieveUserFromCache(uuid);
            if (userData == null) return null;
            return userData.getAssignedHats().stream().filter(hat -> hat.getCosmeticId().equals(playerCosmeticId)).findFirst().orElse(null);
        }

        if (cachedCosmetic != null) return cachedCosmetic;
        cachedCosmetic = Cosmetic.getCosmetic(localCosmeticId);
        return cachedCosmetic;
    }

    public UserCosmetic(UUID uuid, String localCosmeticId, String playerCosmeticId, CosmeticType cosmeticType) {
        this.uuid = uuid;
        this.localCosmeticId = localCosmeticId;
        this.playerCosmeticId = playerCosmeticId;
        this.cosmeticType = cosmeticType;
        this.selected = false;
    }





}
