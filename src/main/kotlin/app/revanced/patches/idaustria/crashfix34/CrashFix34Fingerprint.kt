package app.revanced.patches.idaustria.crashfix34

import app.revanced.patcher.fingerprint.MethodFingerprint
import com.android.tools.smali.dexlib2.AccessFlags

internal object CrashFix34Fingerprint : MethodFingerprint(
    "V",
    parameters = listOf(),
    accessFlags = AccessFlags.PUBLIC.value,
    customFingerprint = { methodDef, _ ->
        methodDef.definingClass.endsWith("Lcom/capacitorjs/plugins/share/SharePlugin;") && methodDef.name == "load"
    }
)
