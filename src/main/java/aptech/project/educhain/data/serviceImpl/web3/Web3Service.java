package aptech.project.educhain.data.serviceImpl.web3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.crypto.Credentials;
import org.web3j.tx.gas.StaticGasProvider;
import org.web3j.protocol.core.methods.response.Log;
import jakarta.annotation.PostConstruct;

import aptech.project.educhain.common.config.contracts.Certification;
import aptech.project.educhain.common.config.contracts.Certification.CertificationIssuedEventResponse;
import aptech.project.educhain.common.config.contracts.EDCToken;
import aptech.project.educhain.common.result.AppResult;
import aptech.project.educhain.common.result.Failure;
import aptech.project.educhain.domain.services.web3.IWeb3Service;

import java.math.BigInteger;
import java.util.List;

@Service
public class Web3Service implements IWeb3Service {

    private Web3j web3j;
    private Credentials credentials;
    private ContractGasProvider gasProvider;
    private EDCToken edcToken;
    private Certification certification;

    @Value("${contract.edc.address}")
    private String edcContractAddress;

    @Value("${contract.certification.address}")
    private String certificationContractAddress;

    @Value("${infura.api.key}")
    private String infuraApiKey;

    @Value("${private.key}")
    private String privateKey;

    @PostConstruct
    public void init() {
        this.web3j = Web3j.build(new HttpService("https://sepolia.infura.io/v3/" + infuraApiKey));

        this.credentials = Credentials.create(privateKey);

        this.gasProvider = new StaticGasProvider(
                BigInteger.valueOf(2_000_000_000L),
                BigInteger.valueOf(300_000));

        this.edcToken = EDCToken.load(edcContractAddress, web3j, credentials, gasProvider);
        this.certification = Certification.load(certificationContractAddress, web3j, credentials, gasProvider);
    }

    @Override
    public AppResult<TransactionReceipt> awardForHomework(String student, BigInteger amount) {
        try {
            TransactionReceipt transactionReceipt = this.edcToken.awardForHomework(student, amount).send();
            return AppResult.successResult(transactionReceipt);
        } catch (Exception e) {
            return AppResult.failureResult(new Failure("Error: " + e.getMessage()));
        }
    }

    @Override
    public AppResult<TransactionReceipt> issueCertification(String recipient, BigInteger issueDate,
            BigInteger teacher, BigInteger student, BigInteger course, BigInteger certificationType) {
        try {
            TransactionReceipt transactionReceipt = this.certification
                    .issueCertification(recipient, issueDate, teacher, student, course, certificationType).send();
            return AppResult.successResult(transactionReceipt);
        } catch (Exception e) {
            return AppResult.failureResult(new Failure("Error: " + e.getMessage()));
        }
    }

    public CertificationIssuedEventResponse getCertificationIssuedEvent(TransactionReceipt transactionReceipt) {
        List<CertificationIssuedEventResponse> events = Certification.getCertificationIssuedEvents(transactionReceipt);
        return events.isEmpty() ? null : events.get(0);
    }
}
