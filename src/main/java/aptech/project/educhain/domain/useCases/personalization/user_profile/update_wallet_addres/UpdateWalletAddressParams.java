package aptech.project.educhain.domain.useCases.personalization.user_profile.update_wallet_addres;

import lombok.Data;

@Data
public class UpdateWalletAddressParams {
    private String walletAddress;
    private Integer userId;
}
