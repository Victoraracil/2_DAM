using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Controls;

namespace Aracil_Victor_EcoCharger.ValidationRules
{
    public class StringValidationRule : ValidationRule
    {
        // Comprobamos que haya texto en el TextBox asociado
        public string Texto { get; set; }

        public override ValidationResult Validate(object value, CultureInfo cultureInfo)
        {
            if (string.IsNullOrWhiteSpace(value?.ToString()))
            {
                return new ValidationResult(false, $"{Texto} es obligatorio");
            }
            else
            {
                if (value.ToString().Length > 200)
                {
                    return new ValidationResult(false, $"{Texto} no debe superar \n los 200 caracteres");
                }
            }
                return ValidationResult.ValidResult;
        }
    }
}
