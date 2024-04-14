package app.revanced.patches.idaustria.crashfix34

import app.revanced.patcher.data.BytecodeContext
import app.revanced.patcher.extensions.InstructionExtensions.addInstruction
import app.revanced.patcher.extensions.InstructionExtensions.replaceInstruction
import app.revanced.patcher.patch.BytecodePatch
import app.revanced.patcher.patch.annotation.CompatiblePackage
import app.revanced.patcher.patch.annotation.Patch

@Patch(
    name = "Fix crash on Android 14+",
    compatiblePackages = [CompatiblePackage("at.gv.oe.app", ["3.1.0"])]
)
@Suppress("unused")
object CrashFix34Patch : BytecodePatch(
    setOf(CrashFix34Fingerprint)
) {
    private val REGISTER_RECEIVER = """
        Landroidx/appcompat/app/AppCompatActivity;->registerReceiver(Landroid/content/BroadcastReceiver;Landroid/content/IntentFilter;I)Landroid/content/Intent;
    """.trimMargin()

    private const val RECEIVER_NOT_EXPORTED = 4

    override fun execute(context: BytecodeContext) {
        val method = CrashFix34Fingerprint.result!!.mutableMethod

        val idx = method.implementation!!.instructions.size - 2
        method.addInstruction(idx, "const v3, 0x$RECEIVER_NOT_EXPORTED")
        method.replaceInstruction(idx + 1, "invoke-virtual {v0, v1, v2, v3}, $REGISTER_RECEIVER")
    }
}
