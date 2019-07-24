package company.pawelzielinski.safeplace.Classes;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;

public class EditTextV2 extends android.support.v7.widget.AppCompatEditText
{
    public EditTextV2( Context context )
    {
        super( context );
    }

    public EditTextV2( Context context, AttributeSet attribute_set )
    {
        super( context, attribute_set );
    }

    public EditTextV2( Context context, AttributeSet attribute_set, int def_style_attribute )
    {
        super( context, attribute_set, def_style_attribute );
    }

    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK &&
                event.getAction() == KeyEvent.ACTION_UP) {
            this.clearFocus();
        }
        return super.dispatchKeyEvent(event);
    }
}
