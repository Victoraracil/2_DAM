using System;
using System.Collections.Generic;
using System.Globalization;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Controls;

namespace Aracil_Victor_EcoCharger.ValidationRules
{
    public class RangoPotenciaValidationRule : ValidationRule
    {
        public int Min { get; set; }
        public int Max { get; set; }

        public override ValidationResult Validate(object value, CultureInfo cultureInfo)
        {
            // Intentamos convertir el valor a número
            if (!int.TryParse(value?.ToString(), out int numero))
            {
                return new ValidationResult(false, "El valor introducido no es \n un número válido");
            }

            // Comprobamos el rango
            if (numero < Min || numero > Max)
            {
                return new ValidationResult(false, $"El valor debe estar entre \n {Min} y {Max}");
            }

            // Todo correcto
            return ValidationResult.ValidResult;
        }
    }
}
