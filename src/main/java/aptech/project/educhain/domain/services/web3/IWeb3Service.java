package aptech.project.educhain.domain.services.web3;

import java.math.BigInteger;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import aptech.project.educhain.common.result.AppResult;

public interface IWeb3Service {

    AppResult<TransactionReceipt> awardForHomework(String student, BigInteger amount);

    AppResult<TransactionReceipt> issueCertification(String recipient, BigInteger issueDate,
            BigInteger teacher, BigInteger student, BigInteger course, BigInteger certificationType);
}