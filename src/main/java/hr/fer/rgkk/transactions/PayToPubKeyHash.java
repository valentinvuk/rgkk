package hr.fer.rgkk.transactions;

import org.bitcoinj.core.*;
import org.bitcoinj.script.Script;
import org.bitcoinj.script.ScriptBuilder;

import static org.bitcoinj.script.ScriptOpCodes.*;

public class PayToPubKeyHash extends ScriptTransaction {

    private final ECKey ecKey = new ECKey();

    public PayToPubKeyHash(WalletKit walletKit, NetworkParameters parameters) {
        super(walletKit, parameters);
    }

    @Override
    public Script createLockingScript() {
        // TODO: Create Locking script
        try {
            return new ScriptBuilder()
                    .data(ecKey.getPubKey())
                    .op(OP_DUP)
                    .op(OP_HASH160)
                    .data(ecKey.getPubKeyHash())
                    .op(OP_EQUALVERIFY)
                    .op(OP_CHECKSIG)
                    .build();
        } catch (Exception e) {
            throw new UnsupportedOperationException();
        }

    }

    @Override
    public Script createUnlockingScript(Transaction unsignedTransaction) {
        try {
            return new ScriptBuilder()
                    .data(sign(unsignedTransaction, ecKey).encodeToBitcoin())
                    .build();
        } catch (Exception e) {
            throw new UnsupportedOperationException();
        }

    }
}
